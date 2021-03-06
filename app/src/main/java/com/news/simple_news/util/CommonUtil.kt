package com.news.simple_news.util

import android.Manifest
import android.content.Context
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.news.simple_news.R
import com.news.simple_news.application.App
import java.io.File
import java.util.*

fun getInstance()= App.instance

fun getString(resId:Int)= getInstance().getString(resId)

fun getColor(color: Int)= ContextCompat.getColor(getInstance(),color)

fun getSystemAssets()= getInstance().assets

fun View.gone(){
    this.visibility=View.GONE
}

fun View.visible(){
    this.visibility=View.VISIBLE
}

fun durationFormat(duration: Long?): String {
    val minute = duration!! / 60
    val second = duration % 60
    return if (minute <= 9) {
        if (second <= 9) {
            "0$minute' 0$second''"
        } else {
            "0$minute' $second''"
        }
    } else {
        if (second <= 9) {
            "$minute' 0$second''"
        } else {
            "$minute' $second''"
        }
    }
}
/**
 * 获取今天日期
 */
fun getTodayDate():String{
    val year= Calendar.getInstance().get(Calendar.YEAR)
    val month= Calendar.getInstance().get(Calendar.MONTH)+1
    val day= Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    return "$year$month$day"
}

fun getWeatherImages(weather: String): Int = when (weather) {
    "多云" -> R.drawable.weather_cloudy
    "多云转阴" -> R.drawable.weather_cloudy
    "多云转晴" -> R.drawable.weather_cloudy
    "中雨" -> R.drawable.weather_moderate_rain
    "中到大雨" -> R.drawable.weather_moderate_rain
    "雷阵雨" -> R.drawable.weather_thundershower
    "阵雨" -> R.drawable.weather_shower
    "阵雨转多云" -> R.drawable.weather_shower
    "暴雪" -> R.drawable.weather_heavy_snowfall
    "暴雨" -> R.drawable.weather_intense_fall
    "大暴雨" -> R.drawable.weather_heavy_snowfall
    "大雪" -> R.drawable.weather_heavy_snow
    "大雨" -> R.drawable.weather_heavy_rain
    "大雨转中雨" -> R.drawable.weather_heavy_rain
    "雷阵雨冰雹" -> R.drawable.weather_hail
    "晴" -> R.drawable.weather_sun
    "沙尘暴" -> R.drawable.weather_sand_storm
    "特大暴雨" -> R.drawable.weather_heavy_downpour
    "雾" -> R.drawable.weather_fog
    "雾霾" -> R.drawable.weather_haze
    "小雪" -> R.drawable.weather_light_snow
    "小雨" -> R.drawable.weather_light_rain
    "阴" -> R.drawable.weather_cloudy_sky
    "雨夹雪" -> R.drawable.weather_sleet
    "阵雪" -> R.drawable.weather_snow_shower
    "中雪" -> R.drawable.weather_moderate_snow
    else -> R.drawable.weather_cloudy
}

fun getWeatherVideo(wea:String?):String{
    val resourceId=when(wea){
        "多云" -> R.raw.cloud
        "多云转阴" -> R.raw.cloud
        "多云转晴" -> R.raw.cloud
        "中雨" -> R.raw.rain
        "中到大雨" -> R.raw.rain
        "雷阵雨" -> R.raw.thundershowers
        "阵雨" -> R.raw.rain
        "阵雨转多云" -> R.raw.rain
        "暴雪" -> R.raw.snow
        "暴雨" -> R.raw.rain
        "大暴雨" -> R.raw.rain
        "大雪" -> R.raw.snow
        "大雨" -> R.raw.rain
        "大雨转中雨" -> R.raw.rain
        "雷阵雨冰雹" -> R.raw.haze
        "晴" -> R.raw.sun
        "沙尘暴" -> R.raw.sand
        "特大暴雨" -> R.raw.rain
        "雾" -> R.raw.fog
        "雾霾" -> R.raw.fog
        "小雪" -> R.raw.snow
        "小雨" -> R.raw.rain
        "阴" -> R.raw.cloud
        "雨夹雪" -> R.raw.rain
        "阵雪" -> R.raw.snow
        "中雪" -> R.raw.snow
        else -> R.raw.cloud
    }
    return "android.resource://${getInstance().packageName}/$resourceId"
}

fun getWeaTextColor(wea:String):Int{
    return when(wea){
        "多云" -> R.color.weather_blue
        "多云转阴" -> R.color.weather_blue
        "多云转晴" -> R.color.weather_blue
        "中雨" -> R.color.weather_rain
        "中到大雨" ->R.color.weather_rain
        "雷阵雨" -> R.color.weather_rain
        "阵雨" -> R.color.weather_rain
        "阵雨转多云" -> R.color.weather_blue
        "暴雪" -> R.color.weather_rain
        "暴雨" -> R.color.weather_rain
        "大暴雨" -> R.color.weather_rain
        "大雪" -> R.color.weather_blue
        "大雨" -> R.color.weather_rain
        "大雨转中雨" -> R.color.weather_rain
        "雷阵雨冰雹" -> R.color.weather_rain
        "晴" -> R.color.weather_blue
        "沙尘暴" -> R.color.weather_sand
        "特大暴雨" -> R.color.weather_rain
        "雾" -> R.color.weather_blue
        "雾霾" -> R.color.weather_blue
        "小雪" -> R.color.weather_blue
        "小雨" -> R.color.weather_rain
        "阴" -> R.color.weather_blue
        "雨夹雪" -> R.color.weather_blue
        "阵雪" -> R.color.weather_blue
        "中雪" -> R.color.weather_blue
        else -> R.raw.cloud
    }
}

//处理城市名，保证能获取到天气
fun returnCityName(cityName: String): String {
    if (cityName.length > 5) {
        return cityName.substring(0, 2)
    }else if (cityName.length<3){
        return cityName
    }
    return cityName.substring(0,cityName.length-1)
}

 val basicPermission= arrayOf( Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.CHANGE_WIFI_STATE,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
    Manifest.permission.WRITE_EXTERNAL_STORAGE)

@RequiresApi(Build.VERSION_CODES.Q)
val androidQPermissions= arrayOf(
    Manifest.permission.INTERNET,
    Manifest.permission.ACCESS_NETWORK_STATE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_COARSE_LOCATION,
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.READ_PHONE_STATE,
    Manifest.permission.ACCESS_WIFI_STATE,
    Manifest.permission.CHANGE_WIFI_STATE,
    Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.ACCESS_BACKGROUND_LOCATION
)