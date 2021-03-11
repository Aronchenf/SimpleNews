package com.news.simple_news.adapter.videodetail

import android.graphics.Typeface
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.news.simple_news.model.bean.Item
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemVideoTextCardBinding
import com.news.simple_news.util.getSystemAssets

class VideoTextCardProvider:BaseItemProvider<Item>() {
    override val itemViewType: Int
        get() = VideoDetailAdapter.TEXT_CARD
    override val layoutId: Int
        get() = R.layout.item_video_text_card

    override fun convert(helper: BaseViewHolder, item: Item) {
        val holder=BaseDataBindingHolder<ItemVideoTextCardBinding>(helper.itemView)
        holder.dataBinding?.bean=item
        holder.dataBinding?.tvTextCard?.typeface= Typeface.createFromAsset(
            getSystemAssets(),
            "fonts/FZLanTingHeiS-L-GB-Regular.TTF"
        )
        holder.dataBinding?.executePendingBindings()
    }
}