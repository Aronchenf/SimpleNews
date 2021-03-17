package com.news.simple_news.ui.weather.citymanage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.cretin.tools.cityselect.CityResponse
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge
import com.news.simple_news.util.toBean
import com.news.simple_news.util.toJson

class CityManageViewModel : BaseViewModel() {

    private val _cityList = MutableLiveData<List<CityManageBean>>()
    val cityList: LiveData<List<CityManageBean>>
        get() = _cityList

    init {
        getCityList()
    }
     fun getCityList() {
        launch({
            _cityList.value = RoomHelper.getCityList()
        })
    }

    fun deleteCity(position: Int) {
        val cityName = cityList.value!![position].city
        launch({
            RoomHelper.deleteCity(cityName)
            getCityList()
        })
    }

}