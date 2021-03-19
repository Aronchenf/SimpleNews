package com.news.simple_news.ui.weather.citychoose

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.cretin.tools.cityselect.callback.OnCitySelectListener
import com.cretin.tools.cityselect.model.CityModel
import com.news.simple_news.R
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentCityChooseBinding
import com.news.simple_news.util.getEventViewModel


class CityChooseFragment : BaseFragment<FragmentCityChooseBinding>() {

    companion object {
        fun newInstance(): CityChooseFragment {
            return CityChooseFragment()
        }
    }

    private val viewModel by lazy { ViewModelProvider(this)[CityChooseViewModel::class.java] }

    override fun initLayout(): Int = R.layout.fragment_city_choose

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.cityView.setSearchTips("请输入城市名称或拼音")
        mBinding.cityView.setOnCitySelectListener(object : OnCitySelectListener {
            override fun onCitySelect(cityModel: CityModel?) {
                viewModel.addCityToDatabase(cityModel!!.cityName)
            }

            override fun onSelectCancel() {
                findNavController().popBackStack()
            }
        })
    }

    override fun observe() {
        mBinding.cityView.bindData(viewModel.allCities.value, viewModel.hotCities.value, null)
        viewModel.mChooseCityInsertResult.observe(this){
            it.let {
                requireActivity().getEventViewModel().addCity.postValue(true)
                requireActivity().getEventViewModel().addChooseCity.postValue(true)
                findNavController().popBackStack()
            }
        }
    }

}
