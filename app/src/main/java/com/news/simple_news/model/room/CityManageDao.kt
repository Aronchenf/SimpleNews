package com.news.simple_news.model.room

import androidx.room.*
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.bean.WeatherBean

@Dao
interface CityManageDao {
    @Insert(entity = CityManageBean::class)
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

    @Query("select * from city_list where location=:isLocation")
    suspend fun getLocationCity(isLocation:Boolean?=true):CityManageBean

    //更新location
    @Query("update city_list set city=:cityName,wea=:cityWea,tem=:cityTem,info=:jsonData where location=:isLocation")
    suspend fun updateLocationCity(cityName: String,cityWea:String,cityTem:String,jsonData:String,isLocation:Boolean=true)
}