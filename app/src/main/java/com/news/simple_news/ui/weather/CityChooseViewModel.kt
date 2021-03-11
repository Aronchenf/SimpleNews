package com.news.simple_news.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.cretin.tools.cityselect.CityResponse
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.util.toBean

class CityChooseViewModel : BaseViewModel() {
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


}