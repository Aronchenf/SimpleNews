package com.news.simple_news.model.bean

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch")
data class WatchRecordBean(
    @NonNull
    @PrimaryKey
    var id:Long,
    @NonNull
    @ColumnInfo(name = "data")
    var dataJson:String,
    @NonNull
    @ColumnInfo(name = "time")
    var watchTime:Long
)