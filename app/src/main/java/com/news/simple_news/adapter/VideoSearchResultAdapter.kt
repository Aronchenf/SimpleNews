package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.Item
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemSearchResultBinding

class VideoSearchResultAdapter :
    BaseQuickAdapter<Item, BaseDataBindingHolder<ItemSearchResultBinding>>(R.layout.item_search_result),
    LoadMoreModule {


    override fun convert(holder: BaseDataBindingHolder<ItemSearchResultBinding>, item: Item) {
        val dataBinding = holder.dataBinding
        dataBinding?.bean = item
        dataBinding?.executePendingBindings()
    }

}


