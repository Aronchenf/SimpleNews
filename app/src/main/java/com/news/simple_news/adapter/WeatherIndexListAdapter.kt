package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.IndexBean
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemWeatherIndexBinding

class WeatherIndexListAdapter :BaseQuickAdapter<IndexBean,BaseDataBindingHolder<ItemWeatherIndexBinding>>(
    R.layout.item_weather_index){
    override fun convert(holder: BaseDataBindingHolder<ItemWeatherIndexBinding>, item: IndexBean) {
        val dataBinding=holder.dataBinding
        dataBinding?.indexBean=item
        dataBinding?.executePendingBindings()
    }

}