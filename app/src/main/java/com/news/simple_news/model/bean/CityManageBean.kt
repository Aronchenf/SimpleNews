package com.news.simple_news.model.bean

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_list")
data class CityManageBean(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    val id:Int,
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