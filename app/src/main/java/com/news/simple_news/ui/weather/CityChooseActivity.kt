package com.news.simple_news.ui.weather

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cretin.tools.cityselect.callback.OnCitySelectListener
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityCityChooseBinding


class CityChooseActivity : BaseActivity<ActivityCityChooseBinding>() {

    companion object {
        fun newInstance(): CityChooseActivity {
            return CityChooseActivity()
        }
    }

    private val viewModel by lazy { ViewModelProvider(this)[CityChooseViewModel::class.java] }


    override fun initLayout(): Int = R.layout.activity_city_choose

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.cityView.setSearchTips("请输入城市名称或拼音")
        mBinding.cityView.setOnCitySelectListener(object : OnCitySelectListener {
            override fun onCitySelect(cityModel: CityModel?) {
                val intent = Intent().apply {
                    putExtra(WeatherFragment.cityName, cityModel!!.cityName)
                }
                setResult(WeatherFragment.chooseCity, intent)
                finish()
            }

            override fun onSelectCancel() {
                finish()
            }

        })
    }

    override fun observe() {
        viewModel.run {
            allCities.observe(this@CityChooseActivity, Observer {
                mBinding.cityView.bindData(it, null, null)
            })
            hotCities.observe(this@CityChooseActivity, Observer {
                mBinding.cityView.bindData(null, it, null)
            })
        }

    }


}
