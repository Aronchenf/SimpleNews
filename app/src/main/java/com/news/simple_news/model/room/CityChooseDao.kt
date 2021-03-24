package com.news.simple_news.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.news.simple_news.model.bean.CityBean

@Dao
interface CityChooseDao{
    @Insert(entity = CityBean::class)
    suspend fun insertAllCity(cityBean: CityBean)

    @Query("select * from city_choose where district like '%'||:cityName||'%'")
    suspend fun getCityList(cityName:String):List<CityBean>

    @Query("delete from city_choose")
    suspend fun deleteAllCity()
}