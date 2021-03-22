package com.news.simple_news.model.api

object API {
    //天气type
    const val weatherType="v1"
    //天气api appId
    const val appId = "52839151"
    //天气api appSecret
    const val appSecret = "Kb4iRkaY"

    const val CAIYUN_TOKEN="mMitGCZ1h0WCHOec"

    //新浪新闻主页
    val NEWS_SINA = "https://interface.sina.cn/"

    //天气api
    val WEATHER_HOST = "https://www.tianqiapi.com/"

    //彩云天气api
    val CAIYUN_HOST="https://api.caiyunapp.com/v2/"

    /**
     * 开眼视频首页精选
     * http://baobab.kaiyanapp.com/api/v2/feed?
     */

    //开眼视频精选主页
    val VIDEO_URL = "http://baobab.kaiyanapp.com/api/"

    //岁月小筑首页
    val PIC_URL="https://img.xjh.me/"
    //随机背景图片
    val PIC_NATURE_URL="https://img.xjh.me/random_img.php?type=bg&ctype=nature&return=json&device=mobile"
    val PIC_ACG_URL="https://img.xjh.me/random_img.php?type=bg&ctype=acg&return=json&device=mobile"

    //随机图片
    val PIC_SUM="https://picsum.photos/"
    val GetCity=0x000001

    val BUNDLE_VIDEO_DATA = "video_data"
    val BUNDLE_CATEGORY_DATA = "category_data"

}