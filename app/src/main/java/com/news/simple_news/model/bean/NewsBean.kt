package com.news.simple_news.model.bean

data class NewsBean(
    var status: Int,
    var message: String,
    var count: Int,
    var data: List<NewsData>
)

data class NewsData(
    var news_date: String,
    var date: String,
    var title: String,
    var wap_title: String,
    var img: String?=null,
    var type: String,
    var pics: PicsBean,
    var link: String,
    var comment_id: String,
    var commentid: String,
    var source: String,
    var intro: String,
    var mediaTypes: String,
    var isSubject: Int,
    var isDujia: Int,
    var newsType: String,
    var docID: String,
    var newsTag: String,
    var comment: Int
)

data class PicsBean(var total: Int, var imgs: List<String>)

