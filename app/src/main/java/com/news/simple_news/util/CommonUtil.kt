package com.news.simple_news.util

import android.view.View
import com.news.simple_news.R
import com.news.simple_news.application.App
import java.util.*

fun getInstance()= App.instance

fun getString(resId:Int)= getInstance().getString(resId)

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