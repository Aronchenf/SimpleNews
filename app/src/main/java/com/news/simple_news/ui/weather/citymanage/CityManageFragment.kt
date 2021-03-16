package com.news.simple_news.ui.weather.citymanage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.news.simple_news.R
import com.news.simple_news.adapter.CityManageAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityManagerBinding
import com.news.simple_news.ui.weather.CityManagerActivity
import com.news.simple_news.util.getEventViewModel
import com.news.simple_news.util.loge
class CityManageFragment : BaseFragment<FragmentCityManagerBinding>() {

    companion object{
        fun newInstance()=CityManageFragment()
    }
    override fun initLayout() = R.layout.fragment_city_manager

    private val mViewModel by lazy { ViewModelProvider(this)[CityManageViewModel::class.java] }
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
            findNavController().navigate(R.id.action_cityManageFragment_to_cityChooseFragment)
        }

        mAdapter.addChildClickViewIds(R.id.btn_city_delete)
        mAdapter.setOnItemChildClickListener { _, _, position ->
            mViewModel.deleteCity(position)
            mAdapter.removeAt(position)
        }
    }

    override fun observe() {
        mViewModel.cityList.observe(this) {
            loge(it.size.toString(),"Manage的List长度为")
            for (bean in it){
                loge(bean.city,"CityManageFragment")
            }
        }

        appViewModel.mCurrentCity.observe(this){
            it?.let {
                requireActivity().getEventViewModel().changeCurrentCity.postValue(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getCityList()
        loge("我们又见面啦")
    }

}