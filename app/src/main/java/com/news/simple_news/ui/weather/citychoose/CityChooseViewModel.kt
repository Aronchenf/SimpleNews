package com.news.simple_news.ui.weather.citychoose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityBean
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.bean.Place
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson

class CityChooseViewModel : BaseViewModel() {

    private val _cityList = MutableLiveData<List<CityBean>>()
    val cityList: LiveData<List<CityBean>>
        get() = _cityList
    private val _mChooseCityInsertResult = MutableLiveData<Long>()
    val mChooseCityInsertResult: LiveData<Long>
        get() = _mChooseCityInsertResult

    private val _hasExist = MutableLiveData<Boolean>()
    val hasExist: LiveData<Boolean>
        get() = _hasExist


//    fun getCityList(query: String) {
//        launch({
//            val bean = repository.getCityListByQuery(query)
//            _cityList.value = bean.places
//        })
//    }

    fun checkCityHasExist(cityName: String){
        launch({
            if (!RoomHelper.checkCityHasExist(cityName)){
                _hasExist.value = false
                addCityToDatabase(cityName)
            }else{
                _hasExist.value = true
            }
        })
    }

    private fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase", "CityChooseViewModel")
        val city=cityName.substring(0,cityName.length-1)
        val weatherDeffer =
            async { repository.getData(API.weatherType, city, API.appId, API.appSecret) }
        launch({
            val weather = weatherDeffer.await()
            val dataBean = weather.data!![0]
            val bean = CityManageBean(cityName, dataBean.wea_day, dataBean.tem, weather.toJson())
            _mChooseCityInsertResult.value = RoomHelper.addCity(bean)
        }, {
            val bean = CityManageBean(city = cityName)
            _mChooseCityInsertResult.value = RoomHelper.addCity(bean)
        })
    }
}