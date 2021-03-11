package com.news.simple_news.model.repository

import com.news.simple_news.model.api.API.NEWS_SINA
import com.news.simple_news.model.api.API.PIC_NATURE_URL
import com.news.simple_news.model.api.API.PIC_URL
import com.news.simple_news.model.api.API.VIDEO_URL
import com.news.simple_news.model.api.API.WEATHER_HOST
import com.news.simple_news.model.api.getRetrofit
import com.news.simple_news.model.bean.*
import okhttp3.ResponseBody

class Repository {

    /**
     * 获取新浪新闻
     */
    suspend fun getNews(type: String, page: Int): NewsBean {
        return getRetrofit(NEWS_SINA).getNews(type, page)
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
        return getRetrofit(WEATHER_HOST).getWeather(version, city, appid, appsecret)
    }

    /**
     * 获取Banner数据
     */
    suspend fun requestVideoData(): VideoBean {
        return getRetrofit(VIDEO_URL).getVideoData()
    }

    /**
     * 加载更多视频
     */
    suspend fun loadMoreVideoData(url: String): VideoBean {
        return getRetrofit(VIDEO_URL).getMoreVideoData(url)
    }

    /**
     * 加载视频详情
     */
    suspend fun requestRelatedData(id: Long): Issue {
        return getRetrofit(VIDEO_URL).getRelatedData(id)
    }

    /**
     * 请求热门关键词的数据
     */
    suspend fun requestHotWordData(): List<String> {
        return getRetrofit(VIDEO_URL).getHotWord()
    }

    /**
     * 搜索关键词返回的结果
     */
    suspend fun getSearchResult(words: String): Issue {
        return getRetrofit(VIDEO_URL).getSearchData(words)
    }

    /**
     * 加载更多搜索返回数据
     */
    suspend fun loadMoreData(url: String): Issue {
        return getRetrofit(VIDEO_URL).getIssueData(url)
    }

    /**
     * 获取图片
     */
    suspend fun requestPic(): PicBean {
        return getRetrofit(PIC_URL).getPic(PIC_NATURE_URL)
    }

    /**
     * 下载图片
     */
//    suspend fun getPic(url: String): Observable<ResponseBody> {
//        return getRetrofit(PIC_URL).savePic(url)
//    }

}