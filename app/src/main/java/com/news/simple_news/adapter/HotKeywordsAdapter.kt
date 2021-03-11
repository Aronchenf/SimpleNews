package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.google.android.flexbox.FlexboxLayoutManager
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemHotwordkeyBinding

class HotKeywordsAdapter :
    BaseQuickAdapter<String, BaseDataBindingHolder<ItemHotwordkeyBinding>>(R.layout.item_hotwordkey) {

    override fun convert(holder: BaseDataBindingHolder<ItemHotwordkeyBinding>, item: String) {
        val dataBinding = holder.dataBinding
        dataBinding?.title = item
        val params = dataBinding?.tvTitle?.layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1.0f
        }
        dataBinding?.executePendingBindings()
    }
}