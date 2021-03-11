package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.SearchHistoryBean
import com.google.android.flexbox.FlexboxLayoutManager
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemSearchHistoryBinding

class SearchHistoryAdapter :
    BaseQuickAdapter<SearchHistoryBean, BaseDataBindingHolder<ItemSearchHistoryBinding>>(
        R.layout.item_search_history
    ) {
    override fun convert(
        holder: BaseDataBindingHolder<ItemSearchHistoryBinding>,
        item: SearchHistoryBean
    ) {
        val dataBinding = holder.dataBinding
        dataBinding?.bean = item
        val params = dataBinding?.tvWords?.layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1.0f
        }
        dataBinding?.executePendingBindings()
    }
}