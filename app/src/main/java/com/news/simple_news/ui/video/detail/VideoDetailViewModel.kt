package com.news.simple_news.ui.video.detail

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.adapter.videodetail.VideoDetailAdapter
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.Data
import com.news.simple_news.model.bean.Item
import com.news.simple_news.model.bean.WatchRecordBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.*


class VideoDetailViewModel : BaseViewModel() {


    private val Video_Small_Card_type = "videoSmallCard"
    private val Text_Card = "textCard"


    private val _recentRelatedVideo = MutableLiveData<List<Item>>()
    val recentRelatedVideo: LiveData<List<Item>>
        get() = _recentRelatedVideo
    private val _itemData = MutableLiveData<Data>()
    val itemData: LiveData<Data>
        get() = _itemData

    //视频播放地址
    val videoUrl = MutableLiveData<String>()

    //背景图片
    private val _backgroundUrlValue=MutableLiveData<String>()
    val backgroundUrlValue :LiveData<String>
    get() = _backgroundUrlValue

    val refreshStatus = ObservableBoolean(false)

    private lateinit var itemInfo: Data
    fun setItemInfo(itemInfo: Data) {
        this.itemInfo = itemInfo
        addWatchRecord(itemInfo)
        addDetail(itemInfo)
        _itemData.value = itemInfo
        loadVideoInfo()
        getRecentRelatedVideoInfo()
    }
    //添加视频详情的简介
    private fun addDetail(data: Data){
        val item= Item("",data,"",0)
        val list= mutableListOf<Item>()
        list.add(item)
        _recentRelatedVideo.value=list
    }

    fun loadVideoInfo() {
        val playInfo = itemInfo.playInfo
        val netType = NetUtil.isWifi(getInstance())
        if (playInfo!!.size > 1) {
            //当前网络是wifi环境下选择高清的视频
            if (netType) {
                for (i in playInfo) {
                    if (i.type == "high") {
                        videoUrl.value = i.url
                        break
                    }
                }
            } else {
                for (i in playInfo) {
                    if (i.type == "normal") {
                        videoUrl.value = i.url
                        break
                    }
                }
            }
        } else {
            videoUrl.value = itemInfo.playUrl
        }
        //设置背景
        val backgroundUrl =
            "${itemInfo.cover.blurred}/thumbnail/${getScreenHeight() - dip2px(250f)}x${getScreenWidth()}"
        _backgroundUrlValue.value = backgroundUrl
    }

    private fun getRecentRelatedVideoInfo() {
        refreshStatus.set(true)
        val id = itemInfo.id ?: 0
        launch({
            val bean = repository.requestRelatedData(id)
            bean.itemList.forEach {
                when (it.type) {
                    Text_Card -> {
                        it.itemType = VideoDetailAdapter.TEXT_CARD
                    }
                    Video_Small_Card_type -> {
                        it.itemType = VideoDetailAdapter.SMALL_CARD
                    }
                }
            }
            val list = mutableListOf<Item>()
            list.addAll(recentRelatedVideo.value!!)
            list.addAll(bean.itemList)
            _recentRelatedVideo.value = list
            refreshStatus.set(false)
        }, {
            refreshStatus.set(false)
        })
    }

    //添加视频播放记录
    private fun addWatchRecord(data: Data){
        val bean= WatchRecordBean(data.id,data.toJson(),System.currentTimeMillis())
        launch({
            RoomHelper.addWatchRecord(bean)
        })
    }
}