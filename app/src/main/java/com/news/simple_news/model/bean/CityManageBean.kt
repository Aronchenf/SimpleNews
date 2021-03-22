package com.news.simple_news.model.bean

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_list")
data class CityManageBean(
    @NonNull
    @PrimaryKey
    val city:String,
    @ColumnInfo(name = "wea")
    val wea:String?=null,
    @ColumnInfo(name = "tem")
    val tem:String?=null,
    @ColumnInfo(name = "info")
    val data:String?=null,
    @ColumnInfo(name = "location")
    val locationCity:Boolean?=false
)