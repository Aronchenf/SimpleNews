package com.news.simple_news.ui.weather.citychoose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.ChinesePlaceBean
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.*

class CityChooseViewModel : BaseViewModel() {

    private val _cityList = MutableLiveData<List<ChinesePlaceBean>>()
    val cityList: LiveData<List<ChinesePlaceBean>>
        get() = _cityList

    private val _mChooseCityInsertResult = MutableLiveData<Long>()
    val mChooseCityInsertResult: LiveData<Long>
        get() = _mChooseCityInsertResult

    private val _hasExist = MutableLiveData<Boolean>()
    val hasExist: LiveData<Boolean>
        get() = _hasExist

    val emptyStatus=MutableLiveData<Boolean>()

    init {
        getCityData()
    }

    //检查数据库是否已经添加了所有城市数据
    private fun getCityData() {
        launch({
            val hasInsert=RoomHelper.checkCityHasInserted("北京市")
            if (hasInsert){
                val jsonString = getJsonStr("chineseCity.json")
                val list = jsonString.toList<ChinesePlaceBean>()
                RoomHelper.insertAllCity(list)
            }
        })
    }


    fun getCityList(query: String) {
        launch({
            val list=RoomHelper.getLikeCityList(query)
            if (list.isEmpty()){
                emptyStatus.value=true
            }else{
                emptyStatus.value=false
                _cityList.value =list
            }
        })
    }

    fun checkCityHasExist(cityName: String) {
        launch({
            if (!RoomHelper.checkCityHasExist(cityName)) {
                _hasExist.value = false
                addCityToDatabase(cityName)
            } else {
                _hasExist.value = true
            }
        })
    }

    //添加城市数据到数据库，如果有网，则存入全部数据，如果没有，则存入城市名
    private fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase", "CityChooseViewModel")
        val city = returnCityName(cityName)
        val weatherDeffer =
            async { repository.getData(API.weatherType, city, API.appId, API.appSecret) }
        launch({
            val weather = weatherDeffer.await()
            val dataBean = weather.data!![0]
            val bean = CityManageBean(0,cityName, dataBean.wea_day, dataBean.tem, weather.toJson(),locationCity = false)
            _mChooseCityInsertResult.value = RoomHelper.addCity(bean)
        }, {
            val bean = CityManageBean(city = cityName,id = 0,locationCity = false)
            _mChooseCityInsertResult.value = RoomHelper.addCity(bean)
        })
    }


}