package com.news.simple_news.ui.weather.citymanage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.R
import com.news.simple_news.adapter.CityManageAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityManagerBinding
import com.news.simple_news.util.loge
class CityManageFragment : BaseFragment<FragmentCityManagerBinding>() {

    companion object{
        fun newInstance()=CityManageFragment()
    }
    override fun initLayout() = R.layout.fragment_city_manager

    private val mViewModel by lazy { ViewModelProvider(requireActivity())[CityManageViewModel::class.java] }
    private val mAdapter by lazy { CityManageAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.toolbar.run {
            setNavigationIcon(R.drawable.arrow_left_black)
            setNavigationOnClickListener { (requireActivity() as CityManagerActivity).onBackPressed() }
            title = getString(R.string.city_manage)
        }
        mBinding.viewModel = mViewModel
        mBinding.adapter = mAdapter
        mBinding.btnAddCity.setOnClickListener {
            (activity as CityManagerActivity).showChooseFragment()
        }
        mAdapter.addChildClickViewIds(R.id.btn_city_delete)
        mAdapter.setOnItemChildClickListener { _, _, position ->
            mViewModel.deleteCity(position)
            mAdapter.removeAt(position)
        }
    }

    override fun observe() {
        mViewModel.cityList.observe(viewLifecycleOwner) {
            for (i in it.indices){
                loge(it[i].city)
            }
        }
    }

    override fun onResume() {
        mViewModel.getCityList()
        super.onResume()
    }

}