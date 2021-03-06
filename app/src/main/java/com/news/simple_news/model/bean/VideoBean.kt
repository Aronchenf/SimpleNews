package com.news.simple_news.model.bean

import java.io.Serializable

data class VideoBean(
    val issueList: List<Issue>,
    val nextPageUrl: String,
    val nextPublishTime: Long,
    val newestIssueType: String,
    val dialog: Any
) : Serializable

data class Issue(
    val releaseTime: Long,
    val type: String,
    val date: Long,
    val total: Int,
    val publishTime: Long,
    val itemList: MutableList<Item>,
    var count: Int,
    val nextPageUrl: String
) : Serializable


data class Item(
    val type: String? = "",
    val data: Data,
    val tag: String? = "",
    var itemType: Int
) : Serializable

data class Data(
    val dataType: String,
    val text: String? = "",
    val videoTitle: String,
    val id: Long,
    val title: String? = "",
    val slogan: String?,
    val description: String,
    val actionUrl: String,
    val provider: Provider,
    val category: String,
    val parentReply: ParentReply,
    val author: Author,
    val cover: Cover,
    val likeCount: Int,
    val playUrl: String,
    val thumbPlayUrl: String,
    val duration: Long,
    val message: String,
    val createTime: Long,
    val webUrl: WebUrl,
    val library: String,
    val user: User,
    val playInfo: List<PlayInfo>?,
    val consumption: Consumption,
    val campaign: Any,
    val waterMarks: Any,
    val adTrack: Any,
    val tags: List<Tag>,
    val type: String,
    val titlePgc: Any,
    val descriptionPgc: Any,
    val remark: String,
    val idx: Int,
    val shareAdTrack: Any,
    val favoriteAdTrack: Any,
    val webAdTrack: Any,
    val date: Long,
    val promotion: Any,
    val label: Any,
    val labelList: Any,
    val descriptionEditor: String,
    val collected: Boolean,
    val played: Boolean,
    val subtitles: Any,
    val lastViewTime: Any,
    val playlists: Any,
    val header: Header,
    val itemList: List<Item>
) : Serializable

data class Tag(
    val id: Int,
    val name: String,
    val actionUrl: String,
    val adTrack: Any
) : Serializable


data class Author( val icon: String, val name: String?, val description: String) :
    Serializable

data class Provider(val name: String, val alias: String, val icon: String) : Serializable

data class Cover(
    val feed: String, val detail: String,
    val blurred: String, val sharing: String, val homepage: String
) : Serializable

data class WebUrl(val raw: String, val forWeibo: String) : Serializable

data class PlayInfo(
    val name: String,
    val url: String,
    val type: String,
    val urlList: List<Url>
) : Serializable

data class Consumption(
    val collectionCount: Int,
    val shareCount: Int,
    val replyCount: Int
) : Serializable

data class User(
    val uid: Long,
    val nickname: String,
    val avatar: String,
    val userType: String,
    val ifPgc: Boolean
) : Serializable

data class ParentReply(val user: User, val message: String) : Serializable

data class Url(val size: Long) : Serializable

data class Header(
    val id: Int,
    val icon: String,
    val iconType: String,
    val description: String,
    val title: String,
    val font: String,
    val cover: String,
    val label: Label,
    val actionUrl: String,
    val subtitle: String,
    val labelList: List<Label>
) : Serializable

data class Label(
    val text: String,
    val card: String,
    val detial: Any,
    val actionUrl: Any
) : Serializable


