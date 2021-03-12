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

    private val _allCities = MutableLiveData<List<CityModel>>()
    val allCities: LiveData<List<CityModel>>
        get() = _allCities

    private val _hotCities = MutableLiveData<List<CityModel>>()
    val hotCities: LiveData<List<CityModel>>
        get() = _hotCities

    init {
        getAllCities()
        getHotCities()
    }

    private fun getAllCities() {
        val allCities = mutableListOf<CityModel>()
        val jsonDeffer = async { ResourceUtils.readAssets2String("city.json") }
        launch({
            val cityJson = jsonDeffer.await()
            val cityResponse = cityJson.toBean<CityResponse>()
            val data = cityResponse.data
            for (item in data) {
                if (item.sons == null) {
                    allCities.add(CityModel(item.name, item.areaId))
                } else {
                    for (son in item.sons) {
                        allCities.add(CityModel(son.name, son.areaId))
                    }
                }
            }
            _allCities.value = allCities
        })
    }

    private fun getHotCities() {
        val hotCities = mutableListOf<CityModel>()
        hotCities.add(CityModel("深圳", "0x01"))
        hotCities.add(CityModel("广州", "0x02"))
        hotCities.add(CityModel("北京", "0x03"))
        hotCities.add(CityModel("武汉", "0x04"))
        hotCities.add(CityModel("上海", "0x05"))
        hotCities.add(CityModel("杭州", "0x06"))
        _hotCities.value = hotCities
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
        })
    }

    fun addCityToDatabase(cityName: String) {
        launch({
            val weather = repository.getData(API.weatherType, cityName, API.appId, API.appSecret)
            val dataBean = weather.data[0]
            val bean = CityManageBean(cityName, dataBean.wea_day, dataBean.tem, weather.toJson())
            RoomHelper.addCity(bean)
            _cityList.value = RoomHelper.getCityList()
        }, {
            val bean = CityManageBean(city = cityName)
            RoomHelper.addCity(bean)
            _cityList.value = RoomHelper.getCityList()
        })
    }
}