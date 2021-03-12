package com.news.simple_news.util

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.news.simple_news.R
import com.news.simple_news.model.bean.Data
import com.news.simple_news.model.bean.IndexBean
import com.news.simple_news.model.bean.Item
import com.news.simple_news.model.bean.NewsData
import com.news.simple_news.ui.video.VideoType

@BindingAdapter("newsPic")
fun bindNewsImage(appCompatImageView: AppCompatImageView, newsData: NewsData) {
    if (newsData.pics.imgs.isNullOrEmpty() && newsData.img.isNullOrEmpty()) {
        appCompatImageView.setImageWithNull()
    } else if (newsData.img.isNullOrEmpty()) {
        appCompatImageView.setImage(newsData.pics.imgs[0])
    } else {
        appCompatImageView.setImage(newsData.img!!)
    }
}

@BindingAdapter("refreshStatus")
fun bindRefresh(swipeRefreshLayout: SwipeRefreshLayout, isRefresh: Boolean) {
    swipeRefreshLayout.isRefreshing = isRefresh
}

@BindingAdapter("weatherImg")
fun bindWeatherImg(appCompatImageView: AppCompatImageView, weather: String?) {
    if (!weather.isNullOrEmpty()){
        appCompatImageView.setImageResource(getWeatherImages(weather))
    }

}

@SuppressLint("SetTextI18n")
@BindingAdapter("indexTitle")
fun bindWeatherIndexTitle(appCompatTextView: AppCompatTextView, indexBean: IndexBean) {
    appCompatTextView.text = "${indexBean.title} : ${indexBean.level}"
}

@BindingAdapter("image")
fun bindImage(appCompatImageView: AppCompatImageView, imageResId: Int) {
    appCompatImageView.setImageResource(imageResId)
}

@BindingAdapter("coverData")
fun bindVideoCover(appCompatImageView: AppCompatImageView, videoType: VideoType) {
    appCompatImageView.setBannerImage(videoType.item?.data?.cover!!.feed)
}

@BindingAdapter("avatarData")
fun bindVideoAvatar(appCompatImageView: AppCompatImageView, videoType: VideoType) {
    videoType.item?.data?.author!!.icon.run {
        if (this.isEmpty()) {
            appCompatImageView.setAvatarImage(R.drawable.default_avatar)
        } else {
            appCompatImageView.setAvatarImage(this)
        }
    }
}

@BindingAdapter("videoTag")
fun bindVideoTag(appCompatTextView: AppCompatTextView, videoType: VideoType) {
    val tagText = StringBuilder()
    val itemData = videoType.item!!.data
    itemData.tags.take(4).forEach {
        tagText.append(it.name).append("/")
    }
    val timeFormat = durationFormat(itemData.duration)
    tagText.append(timeFormat)
    appCompatTextView.text = tagText.toString()
}

@SuppressLint("SetTextI18n")
@BindingAdapter("videoSearchTag")
fun bindSearchTag(appCompatTextView: AppCompatTextView, item: Item) {
    val timeFormat = durationFormat(item.data.duration)
    appCompatTextView.text = "#${item.data.category}/$timeFormat"
}

@BindingAdapter("img")
fun bindSearchImg(appCompatImageView: AppCompatImageView,url:String){
    appCompatImageView.setImage(url)
}

@BindingAdapter("roundImg")
fun bindRoundImg(appCompatImageView: AppCompatImageView,url: String){
    appCompatImageView.setRoundImage(url)
}

@BindingAdapter("backImg")
fun bindBackImg(appCompatImageView: AppCompatImageView,url: String){
    appCompatImageView.setBackground(url)
}

@SuppressLint("SetTextI18n")
@BindingAdapter("historyTag")
fun bindHistoryTag(appCompatTextView: AppCompatTextView, data: Data){
    val author=data.author.name?:""
    appCompatTextView.text = "#${data.category}/$author"
}

@BindingAdapter("isGone")
fun bindHasGone(appCompatTextView: AppCompatTextView,data:String?){
    appCompatTextView.visibility=if (data.isNullOrEmpty()) View.GONE else View.VISIBLE
}



