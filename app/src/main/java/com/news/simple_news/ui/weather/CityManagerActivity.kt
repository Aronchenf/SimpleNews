package com.news.simple_news.ui.weather


import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityCityManageBinding

class CityManagerActivity : BaseActivity<ActivityCityManageBinding>() {

    override fun initLayout() = R.layout.activity_city_manage

    override fun onSupportNavigateUp()=findNavController(R.id.nav_city_manage).navigateUp()

    private val mViewModel by lazy { ViewModelProvider(this)[CityViewModel::class.java] }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mViewModel.getCityData()
    }

}