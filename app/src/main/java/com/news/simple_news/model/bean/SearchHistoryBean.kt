package com.news.simple_news.model.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search")
data class SearchHistoryBean(
    @PrimaryKey
    @ColumnInfo(name = "word")
    var searchKey: String
)



