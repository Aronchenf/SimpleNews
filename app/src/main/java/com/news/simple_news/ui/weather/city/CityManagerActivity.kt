package com.news.simple_news.ui.weather.city

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.news.simple_news.R
import com.news.simple_news.adapter.CityManageAdapter
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityCityManagerBinding
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.ui.weather.CityChooseActivity
import com.news.simple_news.ui.weather.WeatherFragment

class CityManagerActivity : BaseActivity<ActivityCityManagerBinding>() {

    override fun initLayout() = R.layout.activity_city_manager

    companion object {
        const val chooseCity = 0x01
        const val cityName = "cityName"
    }

    private val mViewModel by lazy { ViewModelProvider(this)[CityManageViewModel::class.java] }
    private val mAdapter by lazy { CityManageAdapter() }

    private val startActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == chooseCity) {
                val cityName = activityResult.data?.getStringExtra(cityName)
                mViewModel.addCity(cityName!!)
                mAdapter.addData(CityManageBean(city = cityName))
            }
        }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.toolbar.run {
            setNavigationIcon(R.drawable.arrow_left_black)
            setNavigationOnClickListener { finish() }
            title = getString(R.string.city_manage)
        }
        mBinding.viewModel = mViewModel
        mBinding.adapter = mAdapter
        mBinding.btnAddCity.setOnClickListener {
            val intent = Intent(this, CityChooseActivity::class.java)
            startActivity.launch(intent)
        }
        mAdapter.addChildClickViewIds(R.id.btn_city_delete)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            mAdapter.removeAt(position)
            mViewModel.deleteCity(mAdapter.data[position].city)
        }

    }

}