package com.news.simple_news.model.api

import com.news.simple_news.model.bean.*
import retrofit2.http.*
import retrofit2.http.Url

interface ApiService {

    //新浪新闻
    @Headers("Accept-Encoding: identity")
    @GET("ent/feed.d.json")
    suspend fun getNews(@Query("ch") ch: String, @Query("page") page: Int): NewsBean

    //天气

    @GET("api/")
    suspend fun getWeather(
        @Query("version") v1: String, @Query("city") city: String,
        @Query("appid") appId: String, @Query("appsecret") appSecret: String
    ): WeatherBean

    //彩云天气城市列表
    @GET("place?token=${API.CAIYUN_TOKEN}&lang=zh_CN")
    suspend fun getCityList(@Query("query") city:String):SearchCityBean

    //视频精选
    @GET("v2/feed?num=1")
    suspend fun getVideoData(): VideoBean

    //根据nextPageUrl请求数据下一页数据
    @GET
    suspend fun getMoreVideoData(@Url url: String): VideoBean

    //根据item id 获取相关视频
    @GET("v4/video/related?")
    suspend fun getRelatedData(@Query("id") id: Long): Issue

    /**
     * 获取更多的 Issue
     */
    @GET
    suspend fun getIssueData(@Url url: String): Issue
    /**
     * 获取搜索信息
     */
    @GET("v1/search?&num=10&start=10")
    suspend fun getSearchData(@Query("query") query:String) : Issue

    /**
     * 热门搜索词
     */
    @GET("v3/queries/hot")
    suspend fun getHotWord():ArrayList<String>

    //随机图片
    @GET
    suspend fun getPic(@Url url: String): PicBean
    //保存图片
//    @GET
//    suspend fun savePic(@Url url: String):Observable<ResponseBody>

}