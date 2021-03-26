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

    private val _mChooseCityInsertResult = MutableLiveData<Int>()
    val mChooseCityInsertResult: LiveData<Int>
        get() = _mChooseCityInsertResult


    //打开应用先创建一个LocationCity,占据位置
    private fun createLocationCity() {
        loge("createLocationCity","WeatherViewModel")
        launch({
            val bean = RoomHelper.getLocationCity()
            if (bean == null) {
                RoomHelper.addCity(CityManageBean(id = 0, locationCity = true))
            }
        })
    }

    //添加LocationCity
    fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase","WeatherViewModel")
        launch({
            _mChooseCityInsertResult.value = RoomHelper.updateLocationCityInfo(cityName)
        })
    }

    init {
        createLocationCity()
        getCityList()
    }
     fun getCityList() {
        launch({
            loge("getCityList","WeatherViewModel")
            val list=RoomHelper.getCityList()
            val noNullList= mutableListOf<CityManageBean>()
            for (bean in list){
                if (!bean.city.isNullOrEmpty()){
                   noNullList.add(bean)
                }
            }
            _cityList.value=noNullList
        })
    }

}