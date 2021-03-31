package com.news.simple_news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.NewsData
import com.news.simple_news.ui.news.NewsDetailFragment
import com.news.simple_news.util.startActivity
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemNewsBinding

class NewsAdapter :
    BaseQuickAdapter<NewsData, BaseDataBindingHolder<ItemNewsBinding>>(R.layout.item_news),
    LoadMoreModule {

//    override fun onCreateDefViewHolder(
//        parent: ViewGroup,
//        viewType: Int
//    ): BaseDataBindingHolder<ItemNewsBinding> {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding =
//            DataBindingUtil.inflate<ItemNewsBinding>(inflater, R.layout.item_news, parent, false)
//        return BaseDataBindingHolder<ItemNewsBinding>(binding.root).apply {
//            binding.itemNews.setOnClickListener {
//                val position =
//                    adapterPosition.takeIf { it != NO_POSITION } ?: return@setOnClickListener
//                val webLink = data[position].link
//                startActivity<NewsDetailFragment> {
//                    putExtra(NewsDetailFragment.URL, webLink)
//                }
//            }
//
//        }
//    }

    override fun convert(holder: BaseDataBindingHolder<ItemNewsBinding>, item: NewsData) {
        val dataBinding = holder.dataBinding
        dataBinding?.bean = item
        dataBinding?.executePendingBindings()
    }
}