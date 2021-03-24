package com.news.simple_news.model.bean

data class ChinesePlaceBean(
    val name: String, // 吉林
    val city: List<City>
)

data class City(
    val name: String, // 长春
    val area: List<String>
)