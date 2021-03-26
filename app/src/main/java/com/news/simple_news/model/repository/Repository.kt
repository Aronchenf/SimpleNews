package com.news.simple_news.model.repository

import com.news.simple_news.model.api.API.PIC_NATURE_URL
import com.news.simple_news.model.api.ApiServer
import com.news.simple_news.model.bean.*

class Repository {

    /**
     * 获取新浪新闻
     */
    suspend fun getNews(type: String, page: Int): NewsBean {
        return ApiServer.getRetrofit().getNews(type, page)
    }

    /**
     * 获取天气信息
     */
    suspend fun getData(
        version: String,
        city: String,
        appid: String,
        appsecret: String
    ): WeatherBean {
        return ApiServer.getRetrofit().getWeather(version, city, appid, appsecret)
    }

    /**
     * 获取城市列表
     */
//    suspend fun getCityListByQuery(key:String):SearchCityBean{
//        return getRetrofit(CAIYUN_HOST).getCityList(key)
//    }

    /**
     * 获取Banner数据
     */
    suspend fun requestVideoData(): VideoBean {
        return ApiServer.getRetrofit().getVideoData()
    }

    /**
     * 加载更多视频
     */
    suspend fun loadMoreVideoData(url: String): VideoBean {
        return ApiServer.getRetrofit().getMoreVideoData(url)
    }

    /**
     * 加载视频详情
     */
    suspend fun requestRelatedData(id: Long): Issue {
        return ApiServer.getRetrofit().getRelatedData(id)
    }

    /**
     * 请求热门关键词的数据
     */
    suspend fun requestHotWordData(): List<String> {
        return ApiServer.getRetrofit().getHotWord()
    }

    /**
     * 搜索关键词返回的结果
     */
    suspend fun getSearchResult(words: String): Issue {
        return ApiServer.getRetrofit().getSearchData(words)
    }

    /**
     * 加载更多搜索返回数据
     */
    suspend fun loadMoreData(url: String): Issue {
        return ApiServer.getRetrofit().getIssueData(url)
    }

    /**
     * 获取图片
     */
    suspend fun requestPic(): PicBean {
        return ApiServer.getRetrofit().getPic(PIC_NATURE_URL)
    }

    /**
     * 下载图片
     */
//    suspend fun getPic(url: String): Observable<ResponseBody> {
//        return getRetrofit(PIC_URL).savePic(url)
//    }

}