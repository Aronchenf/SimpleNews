package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.Data
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemWatchHistoryBinding

class WatchHistoryAdapter :
    BaseQuickAdapter<Data, BaseDataBindingHolder<ItemWatchHistoryBinding>>(
        R.layout.item_watch_history
    ) {


    override fun convert(holder: BaseDataBindingHolder<ItemWatchHistoryBinding>, item: Data) {
        val dataBinding = holder.dataBinding
        dataBinding?.bean = item
        dataBinding?.executePendingBindings()
    }
}