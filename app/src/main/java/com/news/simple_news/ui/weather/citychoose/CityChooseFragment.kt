package com.news.simple_news.ui.weather.citychoose

import android.media.MediaRouter2
import android.os.Bundle
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.cretin.tools.cityselect.callback.OnCitySelectListener
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.R
import com.news.simple_news.adapter.CityChooseAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityChooseBinding
import com.news.simple_news.util.getEventViewModel
import com.news.simple_news.util.toast


class CityChooseFragment : BaseFragment<FragmentCityChooseBinding>(){

    companion object {
        fun newInstance(): CityChooseFragment {
            return CityChooseFragment()
        }
    }

    private val viewModel by lazy { ViewModelProvider(this)[CityChooseViewModel::class.java] }
    private val mAdapter by lazy { CityChooseAdapter() }

    override fun initLayout(): Int = R.layout.fragment_city_choose

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.viewModel=this.viewModel
        mBinding.adapter=mAdapter
        mBinding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }

        mBinding.citySearchView.doAfterTextChanged {
            val key=it.toString().trim()
            if (key.isNotEmpty()){
               viewModel.getCityByName(key)
            }
        }

        mAdapter.setOnItemClickListener { _, _, position ->
            val place=mAdapter.getItem(position)
            viewModel.addCityToDatabase(place.name)
        }
    }


    override fun observe() {
        viewModel.mChooseCityInsertResult.observe(this){
            it.let {
                requireActivity().getEventViewModel().addCity.postValue(true)
                requireActivity().getEventViewModel().addChooseCity.postValue(true)
                findNavController().popBackStack()
            }
        }
        viewModel.hasExist.observe(this){
            if (it) toast("城市已经存在")
        }
    }


}
