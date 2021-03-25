package com.news.simple_news.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//已选择城市
@Entity(tableName = "city_list")
data class CityManageBean(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    @ColumnInfo(name = "city")
    val city:String,
    @ColumnInfo(name = "wea")
    val wea:String?="",
    @ColumnInfo(name = "tem")
    val tem:String?="",
    @ColumnInfo(name = "info")
    val data:String?="",
    @ColumnInfo(name = "location")
    val locationCity:Boolean
)