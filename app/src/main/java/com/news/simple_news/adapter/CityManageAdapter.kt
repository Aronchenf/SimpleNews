package com.news.simple_news.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.DraggableModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.news.simple_news.R
import com.news.simple_news.databinding.ItemCityManageBinding
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.util.visible

class CityManageAdapter :BaseQuickAdapter<CityManageBean,BaseDataBindingHolder<ItemCityManageBinding>>(
    R.layout.item_city_manage),DraggableModule {
    @SuppressLint("SetTextI18n")
    override fun convert(
        holder: BaseDataBindingHolder<ItemCityManageBinding>,
        item: CityManageBean
    ) {
        val dataBinding=holder.dataBinding
        dataBinding?.bean=item
        if (item.locationCity){
            dataBinding?.ivLocation?.visible()
        }
        dataBinding?.executePendingBindings()
    }

    override fun onViewAttachedToWindow(holder: BaseDataBindingHolder<ItemCityManageBinding>) {
        super.onViewAttachedToWindow(holder)
        setAnimationWithDefault(AnimationType.values()[1])
    }

}