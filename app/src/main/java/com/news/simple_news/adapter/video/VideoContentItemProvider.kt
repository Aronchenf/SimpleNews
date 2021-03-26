package com.news.simple_news.adapter.video

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.news.simple_news.ui.video.VideoType
import com.news.simple_news.ui.video.detail.VideoDetailActivity
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemHomeContentBinding

class VideoContentItemProvider : BaseItemProvider<VideoType>() {
    override val itemViewType: Int
        get() = VideoType.Type.CONTENT
    override val layoutId: Int
        get() = R.layout.item_home_content

    private lateinit var mContext: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        mContext = parent.context
        val layoutInflater = LayoutInflater.from(mContext)
        val dataBinding = DataBindingUtil.inflate<ItemHomeContentBinding>(
            layoutInflater,
            R.layout.item_home_content,
            parent,
            false
        )
        return BaseViewHolder(dataBinding.root)
    }

    override fun convert(helper: BaseViewHolder, item: VideoType) {
        val holder = BaseDataBindingHolder<ItemHomeContentBinding>(helper.itemView)
        holder.dataBinding?.model = item
        holder.dataBinding?.executePendingBindings()
    }

    override fun onClick(helper: BaseViewHolder, view: View, data: VideoType, position: Int) {
        super.onClick(helper, view, data, position)
        VideoDetailActivity.gotoVideoPlayer(
            mContext as Activity,
            data.item!!.data
        )
    }


}