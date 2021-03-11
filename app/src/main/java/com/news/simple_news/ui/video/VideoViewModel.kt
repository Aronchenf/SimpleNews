package com.news.simple_news.ui.video

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel


class VideoViewModel : BaseViewModel() {
    private var nextPageUrl: String? = null

    private val BANNER_TYPE = "banner2"
    private val TEXT_HEADER_TYPE = "textHeader"

    private val _videoList = MutableLiveData<List<VideoType>>()
    val videoList: LiveData<List<VideoType>>
        get() = _videoList

    val refreshStatus = ObservableBoolean(false)
    val reloadStatus=MutableLiveData<Boolean>()
    val emptyStatus=MutableLiveData<Boolean>()
    init {
        getData()
    }

    /**
     * 获取Banner+一页数据
     */
    fun getData() {
        refreshStatus.set(true)
        reloadStatus.value=false
        launch({
            val bean = repository.requestVideoData()
            nextPageUrl = bean.nextPageUrl
            val bannerItemList = bean.issueList[0].itemList
            bannerItemList.removeAll {
                it.type == BANNER_TYPE
            }
            val videoModel = VideoType(type = VideoType.Type.BANNER, items = bannerItemList)
            val list = mutableListOf<VideoType>()
            list.add(videoModel)
            _videoList.value = list
            refreshStatus.set(false)
            emptyStatus.value=false
        }, {
            refreshStatus.set(false)
            emptyStatus.value=false
            reloadStatus.value=true
        })
    }

    fun loadMoreList() {
        launch({
            val videoModel = repository.loadMoreVideoData(nextPageUrl!!)
            nextPageUrl = videoModel.nextPageUrl
            val list = videoModel.issueList[0].itemList
            list.removeAll { it.type == BANNER_TYPE }
            val videoTypes = mutableListOf<VideoType>().apply {
                addAll(videoList.value!!)
            }
            list.forEach {
                if (it.type == TEXT_HEADER_TYPE) {
                    videoTypes.add(
                        VideoType(
                            type = VideoType.Type.HEADER,
                            item = it
                        )
                    )
                } else {
                    videoTypes.add(
                        VideoType(
                            type = VideoType.Type.CONTENT,
                            item = it
                        )
                    )
                }
            }
            _videoList.value = videoTypes
        })
    }

}

