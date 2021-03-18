package com.news.simple_news.ui.weather

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.R
import com.news.simple_news.model.api.API
import com.news.simple_news.model.api.API.appId
import com.news.simple_news.model.api.API.appSecret
import com.news.simple_news.model.api.API.weatherType
import com.news.simple_news.model.bean.*
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.*
import kotlinx.coroutines.async

class WeatherViewModel : BaseViewModel() {

    val refreshStatus = ObservableBoolean(false)

    private val _cityList = MutableLiveData<List<CityManageBean>>()
    val cityList: LiveData<List<CityManageBean>>
        get() = _cityList

    init {
        getCityList()
    }

     fun getCityList() {
        launch({
            loge("我获取了整个列表","WeatherViewModel")
            _cityList.value = RoomHelper.getCityList()
        })
    }

}