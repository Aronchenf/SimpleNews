package com.news.simple_news.ui.weather

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.HoursBean
import com.news.simple_news.model.bean.IndexBean
import com.news.simple_news.model.bean.WeatherBean
import com.news.simple_news.model.bean.WeatherDataBean
import com.news.simple_news.util.getString
import com.news.simple_news.R

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

    private val type = "v1"
    private val appId = "52839151"
    private val appSecret = "Kb4iRkaY"

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

    init {
        getCityData()
    }

    fun getCityData(city: String = "福州") {
        refreshStatus.set(true)
        emptyStatus.value=true
        launch({
            val weather = repository.getData(type, city, appId, appSecret)
            setData(weather)
            if (weather.data.isNotEmpty()){
                emptyStatus.value=false
            }
            _weekList.value = weather.data
            _hoursList.value = weather.data[0].hours
            setIndexData(weather)
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
        _wea.value = dataBean.wea
        _airLevel.value = if (dataBean.air_level.isEmpty()) " "
        else "${getString(R.string.air)}${dataBean.air_level}"
        _air.value = dataBean.air
        _airTips.value = "\u3000\u3000${dataBean.air_tips}"
    }

    private fun setIndexData(weatherBean: WeatherBean) {
        val indexList = weatherBean.data[0].index
        for (i in 0 ..5) {
            indexList[i].image = indexImageList[i]
        }
        _indexList.value = indexList
    }
}