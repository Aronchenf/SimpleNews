package com.news.simple_news.model.bean

import androidx.annotation.NonNull

data class WeatherBean(
        val cityid: String?="", // 101230101
        @NonNull
        var city: String, // 福州
        val cityEn: String?="", // fuzhou
        val country: String?="", // 中国
        val countryEn: String?="", // China
        val update_time: String?="", // 2021-03-01 08:59:44
        val data: List<WeatherDataBean>?= mutableListOf()
)

data class WeatherDataBean(
    val day: String, // 01日（星期一）
    val date: String, // 2021-03-01
    val week: String, // 星期一
    val wea: String, // 多云转中雨
    val wea_img: String, // yun
    val wea_day: String, // 多云
    val wea_day_img: String, // yun
    val wea_night: String, // 中雨
    val wea_night_img: String, // yu
    val tem: String, // 15℃
    val tem1: String, // 24℃
    val tem2: String, // 14℃
    val humidity: String, // 93%
    val visibility: String, // 10km
    val pressure: String, // 1008
    val win: List<String>,
    val win_speed: String, // <3级转4-5级
    val win_meter: String, // 小于12km/h
    val sunrise: String, // 06:27
    val sunset: String, // 18:03
    val air: String, // 41
    val air_level: String, // 优
    val air_tips: String, // 空气很好，可以外出活动，呼吸新鲜空气，拥抱大自然！
    val alarm: Alarm,
    val hours: List<HoursBean>,
    var index: List<IndexBean>
)

data class Alarm(
    val alarm_type: String,
    val alarm_level: String,
    val alarm_content: String
)

data class HoursBean(
    val hours: String, // 09时
    val wea: String, // 多云
    val wea_img: String, // yun
    val tem: String, // 17
    val win: String, // 西南风
    val win_speed: String // 3-4级
)

data class IndexBean(
    val title: String, // 紫外线指数
    val level: String, // 弱
    val desc: String, // 辐射较弱，涂擦SPF12-15、PA+护肤品。
    var image: Int
)