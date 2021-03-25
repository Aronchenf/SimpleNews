package com.news.simple_news.ui.weather

import android.os.Environment
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.ChinesePlaceBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.getJsonStr
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson
import com.news.simple_news.util.toList
import java.io.File

class CityViewModel : BaseViewModel() {

    fun getCityData() {
        launch({
            val hasInsert=RoomHelper.checkCityHasInserted("北京市")
            if (hasInsert){
                val jsonString = getJsonStr("chineseCity.json")
                val list = jsonString.toList<ChinesePlaceBean>()
                RoomHelper.insertAllCity(list)
            }
        })
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