package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemCityChooseBinding
import com.news.simple_news.model.bean.Place

class CityChooseAdapter :
    BaseQuickAdapter<Place, BaseDataBindingHolder<ItemCityChooseBinding>>(R.layout.item_city_choose) {
    override fun convert(holder: BaseDataBindingHolder<ItemCityChooseBinding>, item: Place) {
        val binding = holder.dataBinding
        binding?.bean = item
        binding?.executePendingBindings()
    }
}