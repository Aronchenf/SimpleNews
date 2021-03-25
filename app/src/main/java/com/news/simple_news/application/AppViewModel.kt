package com.news.simple_news.application

import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.returnCityName
import com.news.simple_news.util.toJson

class AppViewModel : BaseViewModel() {

    val mCurrentCity = MutableLiveData<Int>()

    fun changeCurrentCity(position: Int) {
        mCurrentCity.value = position
    }

    fun upDateAllChooseArea() {
        launch({
            val cityList = mAppRepository.queryAllChooseArea()
            for (bean in cityList) {
                val city = returnCityName(bean.city)
                val idDeffer = async { RoomHelper.getCityIdByCityName(city) }
                val isLocationCity = async { RoomHelper.getIsLocationCityByCityName(city) }
                val weather = async { repository.getData(API.weatherType, city, API.appId, API.appSecret) }
                val dataBean = weather.await().data!![0]
                RoomHelper.updateCityInfo(
                    CityManageBean(
                        idDeffer.await(),
                        city,
                        dataBean.wea_day,
                        dataBean.tem,
                        weather.toJson(),
                        isLocationCity.await()
                    )
                )
            }
        })
    }

}