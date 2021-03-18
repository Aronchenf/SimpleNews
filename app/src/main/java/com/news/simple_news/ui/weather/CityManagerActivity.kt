package com.news.simple_news.ui.weather


import androidx.navigation.findNavController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityCityManageBinding

class CityManagerActivity : BaseActivity<ActivityCityManageBinding>() {

    override fun initLayout() = R.layout.activity_city_manage

    override fun onSupportNavigateUp()=findNavController(R.id.nav_city_manage).navigateUp()

}