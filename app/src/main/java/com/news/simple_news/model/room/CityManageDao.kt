package com.news.simple_news.model.room

import androidx.room.*
import com.news.simple_news.model.bean.CityManageBean

@Dao
interface CityManageDao {
    @Insert(entity = CityManageBean::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityManage(cityManageBean: CityManageBean):Long

    @Update(entity = CityManageBean::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCityInfo(cityManageBean: CityManageBean)

    @Query("select city from city_list where city = (:cityName)")
    suspend fun getCity(cityName: String): CityManageBean

    @Query("select * from city_list where city=(:cityName)")
    suspend fun getInfoByName(cityName: String):CityManageBean

    @Query("select * from city_list")
    suspend fun getCityList(): List<CityManageBean>

    @Delete(entity = CityManageBean::class)
    suspend fun deleteCity(cityManageBean: CityManageBean)
}