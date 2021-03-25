package com.news.simple_news.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.news.simple_news.model.bean.ChinesePlaceBean

@Dao
interface CityChooseDao{
    @Insert(entity = ChinesePlaceBean::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCity(cityList:List<ChinesePlaceBean>)

    @Query("select * from city_choose where district = (:cityName)")
    suspend fun checkHasInsertList(cityName: String):ChinesePlaceBean?

    @Query("select * from city_choose where district like '%'||:cityName||'%'")
    suspend fun getCityList(cityName:String):List<ChinesePlaceBean>

    @Query("delete from city_choose")
    suspend fun deleteAllCity()
}