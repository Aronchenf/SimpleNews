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
                val countryBean = CityBean("${cityBean.name}市", returnProvice(provinceBean.name))
                cityList.add(countryBean)
                for (area in cityBean.area) {
                    val districtBean = CityBean(area, returnProvice(provinceBean.name))
//                         RoomHelper.insertAllCity("$area,${cityBean.name},${provinceBean.name}")
                    cityList.add(districtBean)
                }
            }
        }
        writeJson(cityList.toJson())
    }

    fun returnProvice(name:String):String{
        return when(name){
            "北京","天津","上海","重庆"->"${name}市"
            "内蒙古","西藏"->"${name}自治区"
            "广西"->"${name}壮族自治区"
            "宁夏"->"${name}回族自治区"
            "新疆"->"${name}维吾尔族自治区"
            "澳门","香港","台湾"->name
            else->"${name}省"
        }
    }

    fun writeJson(json: String) {
        var filePath: String? = null
        val hasScard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)
        filePath = if (hasScard) {
            Environment.getExternalStorageDirectory().toString() + File.separator + "hello.text"
        } else {
            Environment.getDownloadCacheDirectory().toString() + File.separator + "hello.text"
        }
        try {
            val file = File(filePath)
            if (!file.exists()) {
                val dir = File(file.parent.toString())
                dir.mkdir()
                file.createNewFile()
            }
            loge(file.path)
           file.bufferedWriter().use {out->
               out.write(json)
           }
        }catch (e:Exception){

        }
    }

}