package com.news.simple_news.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_choose")
data class CityBean(
    @PrimaryKey
    @ColumnInfo(name = "district")
    val district:String,
    @ColumnInfo(name = "province")
    val province:String
)