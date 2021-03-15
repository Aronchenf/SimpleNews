package com.news.simple_news.ui.weather.child

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.R
import com.news.simple_news.adapter.WeatherIndexListAdapter
import com.news.simple_news.adapter.WeatherTodayListAdapter
import com.news.simple_news.adapter.WeatherWeekListAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentWeatherChildBinding
import com.news.simple_news.model.bean.WeatherBean
import com.news.simple_news.ui.MainActivity
import com.news.simple_news.ui.weather.WeatherViewModel
import com.news.simple_news.util.loge
import com.news.simple_news.util.toBean

class WeatherChildFragment : BaseFragment<FragmentWeatherChildBinding>() {

    override fun initLayout() = R.layout.fragment_weather_child

    companion object {
        const val weatherData = "Data"
        fun newInstance(dataInfo: String): WeatherChildFragment {
            return WeatherChildFragment().apply {
                arguments = Bundle().apply {
                    putString(weatherData, dataInfo)
                }
            }
        }
    }

    private val mViewModel by lazy { ViewModelProvider(requireActivity())[WeatherViewModel::class.java] }
    private val todayListAdapter by lazy { WeatherTodayListAdapter() }
    private val weekListAdapter by lazy { WeatherWeekListAdapter() }
    private val indexListAdapter by lazy { WeatherIndexListAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        val argument = arguments?.getString(weatherData).toString()
        val bean=argument!!.toBean<WeatherBean>()
        mViewModel.setWeatherBean(bean)
        mBinding.apply {
            todayAdapter = todayListAdapter
            weekAdapter = weekListAdapter
            indexAdapter = indexListAdapter
            viewModel = mViewModel
        }

        mBinding.scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            (requireActivity() as MainActivity).animateBottomNavigationView(scrollY < 800 && oldScrollY > scrollY)
        }

    }

    override fun observe() {
        mViewModel.run {
//            reloadStatus.observe(viewLifecycleOwner) {
//                mBinding.reloadView.root.isVisible=it
//            }
            emptyStatus.observe(viewLifecycleOwner) {
//                mBinding.emptyView.root.isVisible=it
                mBinding.line.isGone=it
            }
            wea.observe(viewLifecycleOwner) {

            }
        }
    }
}