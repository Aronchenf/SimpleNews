package com.news.simple_news.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson

class MainViewModel :BaseViewModel(){

    private val _mChooseCityInsertResult = MutableLiveData<Long>()
    val mChooseCityInsertResult: LiveData<Long>
        get() = _mChooseCityInsertResult

    fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase", "CityChooseViewModel")
        val weatherDeffer =
            async { repository.getData(API.weatherType, cityName, API.appId, API.appSecret) }
        launch({
            val weather = weatherDeffer.await()
            val dataBean = weather.data!![0]
            val bean = CityManageBean(cityName, dataBean.wea_day, dataBean.tem, weather.toJson())
            _mChooseCityInsertResult.value= RoomHelper.addCity(bean)
        }, {
            val bean = CityManageBean(city = cityName)
            _mChooseCityInsertResult.value= RoomHelper.addCity(bean)
        })
    }
}