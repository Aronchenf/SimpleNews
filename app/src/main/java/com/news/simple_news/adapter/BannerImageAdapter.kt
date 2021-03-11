package com.news.simple_news.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.news.simple_news.model.bean.Item
import com.news.simple_news.util.setImage
import com.youth.banner.adapter.BannerAdapter

class BannerImageAdapter(data:List<Item>) :BannerAdapter<Item, BannerImageAdapter.BannerViewHolder>(data){

    class BannerViewHolder(var view:AppCompatImageView) :RecyclerView.ViewHolder(view)

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val imageView=AppCompatImageView(parent.context)
        imageView.layoutParams=ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.scaleType=ImageView.ScaleType.CENTER_CROP
        return BannerViewHolder(imageView)
    }

    override fun onBindView(holder: BannerViewHolder, data: Item, position: Int, size: Int) {
        holder.view.setImage(data.data.cover.feed)
    }

}