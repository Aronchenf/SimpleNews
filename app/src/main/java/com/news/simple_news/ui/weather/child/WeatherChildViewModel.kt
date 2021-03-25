package com.news.simple_news.ui.weather.child

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.R
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.api.API
import com.news.simple_news.model.bean.*
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.*

class WeatherChildViewModel : BaseViewModel() {
    val refreshStatus = ObservableBoolean(false)

    private val indexImageList = listOf(
        R.drawable.index_sunstroke,
        R.drawable.index_sports,
        R.drawable.index_blood,
        R.drawable.index_cloth,
        R.drawable.index_washcar,
        R.drawable.index_sun
    )

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
        get() = _city

    private val _tem = MutableLiveData<String>()
    val tem: LiveData<String>
        get() = _tem

    private val _wea = MutableLiveData<String>()
    val wea: LiveData<String>
        get() = _wea

    private val _airLevel = MutableLiveData<String>()
    val airLevel: LiveData<String>
        get() = _airLevel

    private val _air = MutableLiveData<String>()
    val air: LiveData<String>
        get() = _air

    private val _airTips = MutableLiveData<String>()
    val airTips: LiveData<String>
        get() = _airTips

    private val _hoursList = MutableLiveData<List<HoursBean>>()
    val hoursList: LiveData<List<HoursBean>>
        get() = _hoursList

    private val _weekList = MutableLiveData<List<WeatherDataBean>>()
    val weekList: LiveData<List<WeatherDataBean>>
        get() = _weekList

    private val _indexList = MutableLiveData<List<IndexBean>>()
    val indexList: LiveData<List<IndexBean>>
        get() = _indexList
    val emptyStatus = MutableLiveData<Boolean>()

    init {
        emptyStatus.value = true
    }

    fun getCityData(city: String) {
        refreshStatus.set(true)
        val idDeffer=async { RoomHelper.getCityIdByCityName(city) }
        val isLocationCity=async { RoomHelper.getIsLocationCityByCityName(city) }
        loge(returnCityName(city))
        launch({
            val weather = repository.getData(API.weatherType, returnCityName(city), API.appId, API.appSecret)
            val dataBean = weather.data!![0]
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
            setData(weather)
            refreshStatus.set(false)
        }, {
            refreshStatus.set(false)
            val weather = RoomHelper.getCityInfoByName(city)
            setData(weather!!)
        })
    }


    private fun setData(weatherBean: WeatherBean) {
        _city.value = weatherBean.city
        if (!weatherBean.data.isNullOrEmpty()) {
            emptyStatus.value = false
            val dataBean = weatherBean.data[0]
            _tem.value = dataBean.tem
            _wea.value = dataBean.wea_day
            _airLevel.value = if (dataBean.air_level.isEmpty()) " "
            else "${getString(R.string.air)}${dataBean.air_level}"
            _air.value = dataBean.air
            _airTips.value = "\u3000\u3000${dataBean.air_tips}"
            _weekList.value = weatherBean.data
            _hoursList.value = weatherBean.data[0].hours
            setIndexData(weatherBean)
        }
    }

    private fun setIndexData(weatherBean: WeatherBean) {
        if (!weatherBean.data!![0].index.isNullOrEmpty()) {
            val indexList = weatherBean.data[0].index
            for (i in 0..5) {
                indexList[i].image = indexImageList[i]
            }
            _indexList.value = indexList
        }

    }
}