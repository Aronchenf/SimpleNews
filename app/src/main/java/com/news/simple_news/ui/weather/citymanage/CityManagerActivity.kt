package com.news.simple_news.ui.weather.citymanage

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityCityManageBinding
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.util.loge

class CityManagerActivity : BaseActivity<ActivityCityManageBinding>() {

    override fun initLayout() = R.layout.activity_city_manage

    private val mViewModel by lazy { ViewModelProvider(this)[CityManageViewModel::class.java] }

    private val cityChooseFragment by lazy { CityChooseFragment.newInstance() }
    private val cityManageFragment by lazy { CityManageFragment.newInstance() }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        loadFragment()
    }

    private fun loadFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.cityContainer, cityManageFragment)
            .add(R.id.cityContainer, cityChooseFragment)
            .show(cityManageFragment)
            .hide(cityChooseFragment)
            .commit()
    }

    fun showChooseFragment() {
        supportFragmentManager.beginTransaction().setCustomAnimations(
            R.anim.slide_in_right,
            R.anim.slide_out_left,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        ).show(cityChooseFragment).commit()
    }

    fun hideChooseFragment() {
        supportFragmentManager.beginTransaction().hide(cityChooseFragment).commit()
    }

    override fun onBackPressed() {
        if (cityChooseFragment.isVisible) {
            hideChooseFragment()
        } else {
            super.onBackPressed()
        }

    }

}