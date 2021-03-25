package com.news.simple_news.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.simple_news.model.bean.*

@Database(
    entities = [SearchHistoryBean::class, WatchRecordBean::class, CityManageBean::class, ChinesePlaceBean::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun search(): SearchDao
    abstract fun watch(): WatchDao
    abstract fun cityManage(): CityManageDao
    abstract fun cityChoose():CityChooseDao
}