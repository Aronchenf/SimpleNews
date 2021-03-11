package com.news.simple_news.model.room

import androidx.annotation.NonNull
import androidx.room.*
import com.news.simple_news.model.bean.WatchRecordBean

@Dao
interface WatchDao {
    @Insert(entity = WatchRecordBean::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchHistory(@NonNull data: WatchRecordBean)

    @Query("select * from watch")
    suspend fun queryAllWatch():MutableList<WatchRecordBean>

    @Query("select * from watch where id=(:wid)")
    suspend fun queryWatch(wid:Long): WatchRecordBean

    @Delete(entity = WatchRecordBean::class)
    suspend fun deleteWatch(data: WatchRecordBean)

    @Query("delete from watch")
    suspend fun deleteAllWatch()
}