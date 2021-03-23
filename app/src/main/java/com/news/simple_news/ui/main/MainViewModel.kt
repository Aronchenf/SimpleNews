package com.news.simple_news.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.GeoCoderSearchUtil
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson

class MainViewModel : BaseViewModel() {

    private val _mChooseCityInsertResult = MutableLiveData<Long>()
    val mChooseCityInsertResult: LiveData<Long>
        get() = _mChooseCityInsertResult

    init {
        createLocationCity()
    }

    //打开应用先创建一个LocationCity
    private fun createLocationCity() {
        launch({
            val bean = RoomHelper.getLocationCity()
            if (bean == null) {
                RoomHelper.addCity(CityManageBean(city = "  ", locationCity = true))
            }
        })
    }

    //添加LocationCity
    fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase", "MainViewModel")
        launch({
            val weatherBean = repository.getData(API.weatherType, cityName, API.appId, API.appSecret)
            val dataBean = weatherBean.data!![0]
            _mChooseCityInsertResult.value = RoomHelper.addCity(
                CityManageBean(
                    cityName,
                    dataBean.wea_day,
                    dataBean.tem,
                    weatherBean.toJson(),
                    true
                )
            )
        })
    }
}