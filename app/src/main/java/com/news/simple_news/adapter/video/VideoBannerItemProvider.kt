package com.news.simple_news.adapter.video

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.news.simple_news.adapter.BannerImageAdapter
import com.news.simple_news.ui.video.VideoType
import com.news.simple_news.ui.video.detail.VideoDetailActivity
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemHomeBannerBinding
import com.youth.banner.indicator.CircleIndicator

class VideoBannerItemProvider : BaseItemProvider<VideoType>() {
    override val itemViewType: Int
        get() = VideoType.Type.BANNER
    override val layoutId: Int
        get() = R.layout.item_home_banner

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext)
        val dataBinding = DataBindingUtil.inflate<ItemHomeBannerBinding>(
            layoutInflater,
            R.layout.item_home_banner,
            parent,
            false
        )
        return BaseViewHolder(dataBinding.root)
    }

    override fun convert(helper: BaseViewHolder, item: VideoType) {
        val holder = BaseDataBindingHolder<ItemHomeBannerBinding>(helper.itemView)
        holder.dataBinding?.banner?.apply {
            adapter = BannerImageAdapter(item.items)
            indicator = CircleIndicator(mContext)
            setOnBannerListener { _, position ->
                VideoDetailActivity.gotoVideoPlayer(
                    mContext as Activity,
                    item.items[position].data
                )
            }
        }
    }
}