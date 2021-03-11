package com.news.simple_news.model.room

import androidx.annotation.NonNull
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.simple_news.model.bean.SearchHistoryBean

@Dao
interface SearchDao {
    @Insert(entity = SearchHistoryBean::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(@NonNull searchHistoryBean: SearchHistoryBean)

    @Query("select word from search")
    suspend fun queryAllSearch():MutableList<SearchHistoryBean>

    @Query("select word from search where word = (:sword)")
    suspend fun querySearch(sword:String): SearchHistoryBean

    @Delete(entity = SearchHistoryBean::class)
    suspend fun deleteSearch(searchHistoryBean: SearchHistoryBean)

    @Query("delete from search")
    suspend fun deleteAllSearch()

//    duration
//    category
//    cover.feed
//    title
//    author.name
//    id
//
//    playinfo.type
//    playurl
//    cover.blurred
//    description
}