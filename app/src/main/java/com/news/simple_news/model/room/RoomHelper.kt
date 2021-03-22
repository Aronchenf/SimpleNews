package com.news.simple_news.model.room

import androidx.room.Room
import com.news.simple_news.model.bean.*
import com.news.simple_news.util.getInstance
import com.news.simple_news.util.toBean

object RoomHelper {
    private val appDatabase by lazy {
        Room.databaseBuilder(getInstance(), AppDatabase::class.java, "kotlin.db").build()
    }

//    private val MIGRATION_1_2=object :Migration(1,2){
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("Drop TABLE android_metadata")
//            database.execSQL("Drop TABLE room_master_table")
////            database.execSQL("Create TABLE 'watch'('id' Long,"+"'dataJson' String,"+"'watchTime' Long,PRIMARY KEY('id'))")
//        }
//    }

    private val searchDao by lazy { appDatabase.search() }
    private val watchDao by lazy { appDatabase.watch() }
    private val cityDao by lazy { appDatabase.cityManage() }

    /*视频搜索记录*/
    suspend fun queryAllSearchHistory(): MutableList<SearchHistoryBean> {
        val list = searchDao.queryAllSearch()
        return if (list.isNullOrEmpty()) {
            mutableListOf()
        } else {
            list
        }
    }

    //添加搜索新数据
    suspend fun addSearchHistory(searchHistoryBean: SearchHistoryBean) {
        searchDao.insertSearchHistory(searchHistoryBean)
    }

    //删除全部搜索记录
    suspend fun deleteAllSearch() = searchDao.deleteAllSearch()

    /** 视频观看记录*/
    //搜索全部观看记录
    suspend fun queryAllWatch(): MutableList<Data> {
        val list = watchDao.queryAllWatch()
        list.sortByDescending { it.watchTime }
        return if (list.isNullOrEmpty()) {
            mutableListOf()
        } else {
            val dataList = mutableListOf<Data>()
            for (item in list) {
                val data = item.dataJson.toBean<Data>()
                dataList.add(data)
            }
            dataList
        }
    }

    suspend fun addWatchRecord(data: WatchRecordBean) {
        watchDao.insertWatchHistory(data)
    }

    suspend fun deleteWatch(id: Long) {
        val data = watchDao.queryWatch(id)
        watchDao.deleteWatch(data)
    }

    suspend fun deleteAllWatch() = watchDao.deleteAllWatch()

    /*
    天气模块城市数据
     */
    //添加城市数据
    suspend fun addCity(bean: CityManageBean): Long? = cityDao.insertCityManage(bean)

    //更新城市数据
    suspend fun updateCityInfo(bean: CityManageBean) = cityDao.updateCityInfo(bean)

    //根据城市名获取城市数据
    suspend fun getCityInfoByName(cityName: String): WeatherBean?{
        val cityManageBean = cityDao.getInfoByName(cityName)
        if (!cityManageBean.data.isNullOrEmpty()) {
            val json = cityManageBean.data
            val weatherBean = json.toBean<WeatherBean>()
            weatherBean.city = cityName
            return weatherBean
        }
        return WeatherBean(city = cityName)
    }

    //删除单个城市数据
    suspend fun deleteCity(city: String) {
        val bean = cityDao.getCity(city)
        cityDao.deleteCity(bean)
    }

    //获取城市数据列表
    suspend fun getCityList() = cityDao.getCityList()

    //获取定位城市数据
    suspend fun getLocationCity() = cityDao.getLocationCity()

    //更新定位城市数据
    suspend fun updateLocation(cityName: String, cityWea: String, cityTem: String, cityJson: String, isLocation: Boolean)
            = cityDao.updateLocationCity(cityName, cityWea, cityTem, cityJson, isLocation)
}