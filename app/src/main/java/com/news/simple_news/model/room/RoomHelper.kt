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
    private val cityChooseDao by lazy { appDatabase.cityChoose() }

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

    //更新定位城市数据
    suspend fun updateLocationCityInfo(cityName: String):Int= cityDao.updateLocationCityInfo(cityName)
    //更新城市数据
    suspend fun updateCityInfo(bean: CityManageBean):Int = cityDao.updateCityInfo(bean)

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

    //根据城市名获取id从而更新城市数据
    suspend fun getCityIdByCityName(cityName: String):Int{
        return cityDao.getInfoByName(cityName).id
    }

    //根据城市名获取是否定位城市从而更新城市数据
    suspend fun getIsLocationCityByCityName(cityName: String):Boolean{
        return cityDao.getInfoByName(cityName).locationCity
    }

    //删除单个城市数据
    suspend fun deleteCity(city: String) {
        val bean = cityDao.getInfoByName(city)
        cityDao.deleteCity(bean)
    }

    //获取城市数据列表
    suspend fun getCityList() = cityDao.getCityList()

    //获取定位城市数据
    suspend fun getLocationCity() = cityDao.getLocationCity()

    //确定城市是否已经存在  null false
    suspend fun checkCityHasExist(cityName: String):Boolean{
        val city= cityDao.getCityHasExist(cityName)
        return !city.isNullOrEmpty()
    }

    /**
     * CityChoose
     */

    //插入城市数据
    suspend fun insertAllCity(list: List<ChinesePlaceBean>)= cityChooseDao.insertAllCity(list)

    //检查是否已经插入所有城市数据  null true
    suspend fun checkCityHasInserted(cityName: String):Boolean{
        val bean= cityChooseDao.checkHasInsertList(cityName)
        return bean==null
    }
    //查询符合条件数据
    suspend fun getLikeCityList(query:String):List<ChinesePlaceBean>{
        return cityChooseDao.getCityList(query)
    }

    //删除所有城市数据
    suspend fun deleteAllCity()= cityChooseDao.deleteAllCity()

}