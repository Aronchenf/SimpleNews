package com.news.simple_news.ui.weather.citymanage

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.jeremyliao.liveeventbus.LiveEventBus
import com.news.simple_news.R
import com.news.simple_news.adapter.CityManageAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityManagerBinding
import com.news.simple_news.ui.weather.CityManagerActivity
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
            setNavigationOnClickListener {
                (requireActivity() as CityManagerActivity).onBack(mAdapter.data.size)
            }
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

    override fun onResume() {
        super.onResume()
        LiveEventBus.get("added",Boolean::class.java).observe(this,
            Observer<Boolean> { t ->
                if (t){
                    getCityList()
                }
            })
    }
    private fun getCityList(){
        mViewModel.getCityList()
    }

}