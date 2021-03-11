package com.news.simple_news.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.model.bean.HoursBean
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemWeatherTodayBinding


class WeatherTodayListAdapter :
    BaseQuickAdapter<HoursBean, BaseDataBindingHolder<ItemWeatherTodayBinding>>(R.layout.item_weather_today) {
    override fun convert(holder: BaseDataBindingHolder<ItemWeatherTodayBinding>, item: HoursBean) {
        val dataBinding=holder.dataBinding
        dataBinding?.bean=item
        dataBinding?.executePendingBindings()
    }


}