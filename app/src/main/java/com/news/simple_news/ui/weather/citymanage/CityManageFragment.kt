package com.news.simple_news.ui.weather.citymanage

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.R
import com.news.simple_news.adapter.CityManageAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityManagerBinding
import com.news.simple_news.util.*

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
                popBack()
            }
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.add_city->{
                        nav().navigate(R.id.action_cityManageFragment_to_cityChooseFragment)
                    }
                    R.id.setting_city->{

                    }
                }
                true
            }
            title = getString(R.string.city_manage)
        }
        mBinding.viewModel = mViewModel
        mBinding.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            appViewModel.changeCurrentCity(position)
            popBack()
        }

        mAdapter.addChildClickViewIds(R.id.iv_clear)
        mAdapter.setOnItemLongClickListener { _, _, position ->
            mAdapter.getViewByPosition(position,R.id.linear_clear)?.visible()
            true
        }
        mAdapter.setOnItemChildClickListener { _, _, position ->
            mViewModel.deleteCity(position)
            requireActivity().getEventViewModel().deleteCity.postValue(true)
            mAdapter.notifyItemChanged(position)
            mAdapter.getViewByPosition(position,R.id.linear_clear)?.gone()
        }
    }




    override fun observe() {
        requireActivity().getEventViewModel().addChooseCity.observe(viewLifecycleOwner){
            it.let {
                mViewModel.getCityList()
            }
        }
        appViewModel.mCurrentCity.observe(viewLifecycleOwner){
            it.let {
                requireActivity().getEventViewModel().changeCurrentCity.postValue(true)
            }

        }
    }


}