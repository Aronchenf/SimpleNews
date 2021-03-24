package com.news.simple_news.ui.weather

import android.os.Environment
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.ChinesePlaceBean
import com.news.simple_news.model.bean.CityBean
import com.news.simple_news.util.getJsonStr
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson
import com.news.simple_news.util.toList
import java.io.File
import java.io.FileOutputStream

class CityViewModel : BaseViewModel() {


    fun getCityData() {

        val jsonString = getJsonStr("city.json")
        val list = jsonString.toList<ChinesePlaceBean>()
        val cityList = mutableListOf<CityBean>()
        for (provinceBean in list) {
            for (cityBean in provinceBean.city) {
                val countryBean = CityBean("${cityBean.name}市", "${provinceBean.name}省")
                cityList.add(countryBean)
                for (area in cityBean.area) {
                    val districtBean = CityBean(area, "${provinceBean.name}省")
//                         RoomHelper.insertAllCity("$area,${cityBean.name},${provinceBean.name}")
                    cityList.add(districtBean)
                }
            }
        }
        writeFile(cityList.toJson())
        loge(cityList.toJson(), "AllCity")
    }

    private fun writeFile(json: String) {
        val fileName = "../config/myfile.txt"
        val myfile = File(fileName)

        myfile.bufferedWriter().use { out ->
            out.write(json)
        }

    }
}