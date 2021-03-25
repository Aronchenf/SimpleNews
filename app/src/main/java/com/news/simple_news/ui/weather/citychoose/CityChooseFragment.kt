package com.news.simple_news.ui.weather.citychoose

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.news.simple_news.R
import com.news.simple_news.adapter.CityChooseAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityChooseBinding
import com.news.simple_news.util.*


class CityChooseFragment : BaseFragment<FragmentCityChooseBinding>() {

    companion object {
        fun newInstance(): CityChooseFragment {
            return CityChooseFragment()
        }
    }

    private val viewModel by lazy { ViewModelProvider(this)[CityChooseViewModel::class.java] }
    private val mAdapter by lazy { CityChooseAdapter() }

    override fun initLayout(): Int = R.layout.fragment_city_choose

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.viewModel = this.viewModel
        mBinding.adapter = mAdapter
        mBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        mBinding.citySearchView.doAfterTextChanged {
            val key = it.toString().trim()
            if (key.isNotEmpty()) {
                viewModel.getCityList(key)
            }
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            val address = mAdapter.getItem(position)
            val district = address.district
            viewModel.checkCityHasExist(district)
            mBinding.citySearchView.hideSoftInput()
        }
    }

    override fun observe() {
        viewModel.mChooseCityInsertResult.observe(this) {
            it.let {
                requireActivity().getEventViewModel().addCity.postValue(true)
                requireActivity().getEventViewModel().addChooseCity.postValue(true)
                findNavController().popBackStack()
            }
        }
        viewModel.hasExist.observe(this) {
            if (it) toast("城市已经存在")
        }
        viewModel.emptyStatus.observe(this) {
             mBinding.emptyView.root.visibility=if (it)View.VISIBLE else View.GONE
        }
    }


}
