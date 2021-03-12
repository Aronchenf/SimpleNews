package com.news.simple_news.ui.weather.citymanage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.cretin.tools.cityselect.callback.OnCitySelectListener
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.R
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityChooseBinding
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson
import com.news.simple_news.util.toast


class CityChooseFragment : BaseFragment<FragmentCityChooseBinding>() {

    companion object {
        fun newInstance(): CityChooseFragment {
            return CityChooseFragment()
        }
    }

    private val viewModel by lazy { ViewModelProvider(requireActivity())[CityManageViewModel::class.java] }

    override fun initLayout(): Int = R.layout.fragment_city_choose

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.cityView.setSearchTips("请输入城市名称或拼音")
        mBinding.cityView.setOnCitySelectListener(object : OnCitySelectListener {
            override fun onCitySelect(cityModel: CityModel?) {
                viewModel.addCityToDatabase(cityModel!!.cityName)
                (activity as CityManagerActivity).hideChooseFragment()
            }

            override fun onSelectCancel() {
                (activity as CityManagerActivity).hideChooseFragment()
            }
        })
    }

    override fun observe() {
        mBinding.cityView.bindData(viewModel.allCities.value, viewModel.hotCities.value, null)
    }

}
