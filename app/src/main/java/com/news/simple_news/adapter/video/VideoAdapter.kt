package com.news.simple_news.adapter.video

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.news.simple_news.ui.video.VideoType


class VideoAdapter : BaseProviderMultiAdapter<VideoType>(),LoadMoreModule {

    init {
        addItemProvider(VideoBannerItemProvider())
        addItemProvider(VideoHeaderItemProvider())
        addItemProvider(VideoContentItemProvider())
    }

    override fun getItemType(data: List<VideoType>, position: Int): Int {
        return data[position].type
    }

}