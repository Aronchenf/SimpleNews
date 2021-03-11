package com.news.simple_news.adapter.videodetail

import com.chad.library.adapter.base.BaseProviderMultiAdapter
import com.news.simple_news.model.bean.Item
class VideoDetailAdapter : BaseProviderMultiAdapter<Item>() {

    companion object {
        const val DETAIL_INFO = 0
        const val TEXT_CARD = 1
        const val SMALL_CARD = 2
    }

    init {
        addItemProvider(VideoDetailInfoProvider())
        addItemProvider(VideoSmallCardProvider())
        addItemProvider(VideoTextCardProvider())
    }

    override fun getItemType(data: List<Item>, position: Int): Int {
        return if (position==0){
            DETAIL_INFO
        }else{
            data[position].itemType
        }
    }
}