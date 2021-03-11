package com.news.simple_news.adapter.video

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.news.simple_news.ui.video.VideoType
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemHomeHeaderBinding

class VideoHeaderItemProvider :BaseItemProvider<VideoType>(){
    override val itemViewType: Int
        get() = VideoType.Type.HEADER
    override val layoutId: Int
        get() = R.layout.item_home_header

    override fun convert(helper: BaseViewHolder, item: VideoType) {
        val holder=BaseDataBindingHolder<ItemHomeHeaderBinding>(helper.itemView)
        holder.dataBinding?.bean=item
        holder.dataBinding?.executePendingBindings()
    }

}