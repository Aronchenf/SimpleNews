package com.news.simple_news.adapter.videodetail

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.news.simple_news.R

import com.news.simple_news.model.bean.Item
import com.news.simple_news.adapter.videodetail.VideoDetailAdapter
import com.news.simple_news.databinding.ItemVideoDetailInfoBinding

class VideoDetailInfoProvider:BaseItemProvider<Item>() {
    override val itemViewType: Int
        get() = VideoDetailAdapter.DETAIL_INFO
    override val layoutId: Int
        get() = R.layout.item_video_detail_info

    override fun convert(helper: BaseViewHolder, item: Item) {
        val holder=BaseDataBindingHolder<ItemVideoDetailInfoBinding>(helper.itemView)
        holder.dataBinding?.bean=item
        holder.dataBinding?.executePendingBindings()
    }
}