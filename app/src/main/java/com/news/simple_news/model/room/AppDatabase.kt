package com.news.simple_news.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.bean.SearchHistoryBean
import com.news.simple_news.model.bean.WatchRecordBean

@Database(entities = [SearchHistoryBean::class, WatchRecordBean::class,CityManageBean::class],version = 1,exportSchema = false)
abstract class AppDatabase:RoomDatabase(){
    abstract fun search(): SearchDao
    abstract fun watch(): WatchDao
    abstract fun cityManage():CityManageDao
}