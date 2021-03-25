package com.news.simple_news.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//所有城市
@Entity(tableName = "city_choose")
data class ChinesePlaceBean(
    @PrimaryKey
    @ColumnInfo(name = "district")
    val district:String,
    @ColumnInfo(name = "province")
    val province:String
)