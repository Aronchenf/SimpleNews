package com.news.simple_news.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.bean.WeatherBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge
import com.news.simple_news.util.returnCityName
import com.news.simple_news.util.toJson

class MainViewModel : BaseViewModel() {

    private val _mChooseCityInsertResult = MutableLiveData<Int>()
    val mChooseCityInsertResult: LiveData<Int>
        get() = _mChooseCityInsertResult

    init {
        createLocationCity()
    }

    //打开应用先创建一个LocationCity,占据位置
    private fun createLocationCity() {
        loge("createLocationCity", "MainViewModel")
        launch({
            val bean = RoomHelper.getLocationCity()
            if (bean == null) {
                RoomHelper.addCity(CityManageBean(id = 0, locationCity = true,city = ""))
            }
        })
    }

    //添加LocationCity
    fun addCityToDatabase(cityName: String) {
        loge("addCityToDatabase", "MainViewModel")
        launch({
            _mChooseCityInsertResult.value=RoomHelper.updateCityInfo(
                CityManageBean(
                   id = 0,city = cityName,locationCity = true
                )
            )
//            val weatherBean =
//                repository.getData(API.weatherType, returnCityName(cityName), API.appId, API.appSecret)
//            val dataBean = weatherBean.data!![0]
//            _mChooseCityInsertResult.value = RoomHelper.updateCityInfo(
//                CityManageBean(
//                    1,
//                    cityName,
//                    dataBean.wea_day,
//                    dataBean.tem,
//                    weatherBean.toJson(),
//                    true
//                )
//            )
        }
//            , {
//            val weatherBean = CityManageBean(1,city = cityName,locationCity = true)
//            _mChooseCityInsertResult.value = RoomHelper.updateCityInfo(weatherBean)
//        }
        )
    }

}