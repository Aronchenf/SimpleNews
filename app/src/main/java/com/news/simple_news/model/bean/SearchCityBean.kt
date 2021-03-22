package com.news.simple_news.model.bean

data class SearchCityBean(
    val status: String, // ok
    val query: String, // "翔安"
    val places: List<Place>
)

data class Place(
    val id: String, // B025003WPY
    val name: String, // 翔安区
    val formatted_address: String, // 中国 福建省 厦门市 翔安区 翔安区
    val location: Location,
    val place_id: String // a-B025003WPY
)

data class Location(
    val lat: Double, // 24.618544
    val lng: Double // 118.248034
)