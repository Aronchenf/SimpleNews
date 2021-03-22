package com.news.simple_news.ui.weather.citychoose

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.cretin.tools.cityselect.CityResponse
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.bean.Place
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge
import com.news.simple_news.util.toBean
import com.news.simple_news.util.toJson

class CityChooseViewModel : BaseViewModel() {

    private val _cityList = MutableLiveData<List<Place>>()
    val cityList: LiveData<List<Place>>
        get() = _cityList
    private val _mChooseCityInsertResult = MutableLiveData<Long>()
    val mChooseCityInsertResult: LiveData<Long>
        get() = _mChooseCityInsertResult

    private val _hasExist = MutableLiveData<Boolean>()
    val hasExist: LiveData<Boolean>
        get() = _hasExist


    fun getCityList(query: String) {
        launch({
            val bean = repository.getCityListByQuery(query)
            _cityList.value = bean.places
        })
    }

    fun getCityByName(cityName: String) {
        launch({
            val bean = RoomHelper.getCityInfoByName(cityName)
            if (bean == null) {
                _hasExist.value = false
                addCityToDatabase(cityName)
            } else {
                _hasExist.value = true
            }
        })
    }

    fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase", "CityChooseViewModel")
        val weatherDeffer =
            async { repository.getData(API.weatherType, cityName, API.appId, API.appSecret) }
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