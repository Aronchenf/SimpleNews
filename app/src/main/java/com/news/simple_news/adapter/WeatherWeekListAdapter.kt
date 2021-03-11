package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.WeatherDataBean
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemWeatherWeekBinding

class WeatherWeekListAdapter :
    BaseQuickAdapter<WeatherDataBean, BaseDataBindingHolder<ItemWeatherWeekBinding>>(R.layout.item_weather_week) {

    override fun convert(
        holder: BaseDataBindingHolder<ItemWeatherWeekBinding>,
        item: WeatherDataBean
    ) {
        val dataBinding = holder.dataBinding
        dataBinding?.bean = item
        dataBinding?.executePendingBindings()
    }

}