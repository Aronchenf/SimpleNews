package com.news.simple_news.ui.weather.citymanage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge

class CityManageViewModel : BaseViewModel() {

    private val _cityList = MutableLiveData<List<CityManageBean>>()
    val cityList: LiveData<List<CityManageBean>>
        get() = _cityList

    init {
        getCityList()
    }
     fun getCityList() {
         loge("getCityList","CityManageViewModel")
        launch({
            val list=RoomHelper.getCityList()
            val noNullCityList= mutableListOf<CityManageBean>()
            for (bean in list){
                if (bean.city.isNotEmpty()){
                    noNullCityList.add(bean)
                }
            }
            _cityList.value = noNullCityList
        })
    }

    fun deleteCity(position: Int) {
        loge("deleteCity","CityManageViewModel")
        val cityName = cityList.value!![position].city
        launch({
            RoomHelper.deleteCity(cityName)
            getCityList()
        })
    }

}