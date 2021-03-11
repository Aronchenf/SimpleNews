package com.news.simple_news.adapter.videodetail

import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.news.simple_news.model.bean.Item
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemVideoSmallCardBinding

class VideoSmallCardProvider:BaseItemProvider<Item>() {
    override val itemViewType: Int
        get() = VideoDetailAdapter.SMALL_CARD
    override val layoutId: Int
        get() = R.layout.item_video_small_card

    override fun convert(helper: BaseViewHolder, item: Item) {
        val holder=BaseDataBindingHolder<ItemVideoSmallCardBinding>(helper.itemView)
        holder.dataBinding?.bean=item
        holder.dataBinding?.executePendingBindings()
    }
}