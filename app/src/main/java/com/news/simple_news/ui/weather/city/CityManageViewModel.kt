package com.news.simple_news.ui.weather.city

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper

class CityManageViewModel :BaseViewModel() {

    private val _cityList=MutableLiveData<List<CityManageBean>>()
    val cityList:LiveData<List<CityManageBean>>
    get() = _cityList

    init {
        getCityList()
    }
    private fun getCityList(){
        launch({
            _cityList.value=RoomHelper.getCityList()
        })
    }

    fun addCity(cityName:String){
        launch({
            RoomHelper.addCity(CityManageBean(city = cityName))
        })
    }

    fun deleteCity(cityName: String){
        launch({
            RoomHelper.deleteCity(cityName)
        })
    }
}