package com.news.simple_news.model.room

import androidx.room.*
import com.news.simple_news.model.bean.CityManageBean

@Dao
interface CityManageDao {
    @Transaction
    @Insert(entity = CityManageBean::class)
    suspend fun insertCityManage(cityManageBean: CityManageBean): Long?

    @Transaction
    @Query("update city_list set city=(:cityName) where location=1")
    suspend fun updateLocationCityInfo(cityName: String):Int

    @Transaction
    @Update(entity = CityManageBean::class,onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateCityInfo(cityManageBean: CityManageBean):Int

    @Transaction
    @Query("select city from city_list where city = (:cityName)")
    suspend fun getCityHasExist(cityName: String): String?

    @Transaction
    @Query("select * from city_list where city=(:cityName)")
    suspend fun getInfoByName(cityName: String):CityManageBean

    @Transaction
    @Query("select * from city_list")
    suspend fun getCityList(): List<CityManageBean>

    @Transaction
    @Delete(entity = CityManageBean::class)
    suspend fun deleteCity(cityManageBean: CityManageBean)

    @Transaction
    @Query("select * from city_list where location=:isLocation")
    suspend fun getLocationCity(isLocation:Boolean?=true):CityManageBean?

    @Transaction
    //更新location
    @Query("update city_list set city=:cityName,wea=:cityWea,tem=:cityTem,info=:jsonData where location=:isLocation")
    suspend fun updateLocationCity(cityName: String,cityWea:String,cityTem:String,jsonData:String,isLocation:Boolean=true)
}