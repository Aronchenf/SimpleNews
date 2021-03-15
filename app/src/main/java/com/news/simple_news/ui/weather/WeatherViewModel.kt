package com.news.simple_news.ui.weather

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.util.getString
import com.news.simple_news.R
import com.news.simple_news.model.api.API
import com.news.simple_news.model.api.API.appId
import com.news.simple_news.model.api.API.appSecret
import com.news.simple_news.model.api.API.weatherType
import com.news.simple_news.model.bean.*
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.toBean
import com.news.simple_news.util.toJson
import kotlinx.coroutines.async

class WeatherViewModel : BaseViewModel() {

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

    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus=MutableLiveData<Boolean>()

    private val _cityList = MutableLiveData<List<CityManageBean>>()

    val cityList: LiveData<List<CityManageBean>>
        get() = _cityList

    init {
//        getCityList()
    }

    fun getCityList() {
        refreshStatus.set(true)
        val list= mutableListOf<CityManageBean>()
        val deffer=async {
            _cityList.value = RoomHelper.getCityList()
            for (i in cityList.value!!.indices){
                val bean=cityList.value!![i]
                val weatherBean=repository.getData(weatherType,bean.city, appId, appSecret)
                val dataBean=weatherBean.data[0]
                val cityBean=CityManageBean(bean.city,dataBean.wea_day,dataBean.tem,weatherBean.toJson())
                list.add(cityBean)
            }

        }
        launch({
            deffer.await()
            refreshStatus.set(true)
            _cityList.value=list
        },{
            refreshStatus.set(true)
        })
    }

    fun setWeatherBean(bean: WeatherBean){
        setData(bean)
    }

    fun getCityData(city: String = "福州") {
        refreshStatus.set(true)
        emptyStatus.value=true
        launch({
            val weather = repository.getData(weatherType, city, appId, appSecret)
            setData(weather)
            if (weather.data.isNotEmpty()){
                emptyStatus.value=false
            }
            refreshStatus.set(false)
            reloadStatus.value=false
        }, {
            refreshStatus.set(false)
            emptyStatus.value=false
            reloadStatus.value=true
        })
    }

    private fun setData(weatherBean: WeatherBean) {
        _city.value = weatherBean.city
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

    private fun setIndexData(weatherBean: WeatherBean) {
        val indexList = weatherBean.data[0].index
        for (i in 0 ..5) {
            indexList[i].image = indexImageList[i]
        }
        _indexList.value = indexList
    }
}