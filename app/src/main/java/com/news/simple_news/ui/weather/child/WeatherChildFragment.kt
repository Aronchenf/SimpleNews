package com.news.simple_news.ui.weather.child

import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.R
import com.news.simple_news.adapter.WeatherIndexListAdapter
import com.news.simple_news.adapter.WeatherTodayListAdapter
import com.news.simple_news.adapter.WeatherWeekListAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentWeatherChildBinding
import com.news.simple_news.ui.main.MainFragment
import com.news.simple_news.ui.weather.WeatherFragment
import com.news.simple_news.util.getWeaTextColor

class WeatherChildFragment : BaseFragment<FragmentWeatherChildBinding>() {

    override fun initLayout() = R.layout.fragment_weather_child

    companion object {
        const val city = "cityName"
        fun newInstance(cityName: String): WeatherChildFragment {
            return WeatherChildFragment().apply {
                arguments = Bundle().apply {
                    putString(city, cityName)
                }
            }
        }
    }
    private val mViewModel by lazy { ViewModelProvider(this)[WeatherChildViewModel::class.java] }
    private val todayListAdapter by lazy { WeatherTodayListAdapter() }
    private val weekListAdapter by lazy { WeatherWeekListAdapter() }
    private val indexListAdapter by lazy { WeatherIndexListAdapter() }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            todayAdapter = todayListAdapter
            weekAdapter = weekListAdapter
            indexAdapter = indexListAdapter
            viewModel = mViewModel
        }
        mBinding.scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            ((parentFragment as WeatherFragment).parentFragment as MainFragment).animateBottomNavigationView(scrollY < 800 && oldScrollY > scrollY)
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        val cityName=arguments?.getString(city).toString()
        mViewModel.getCityData(cityName)
        mBinding.swipeLayout.setOnRefreshListener {
            mViewModel.getCityData(cityName)
        }
    }

    override fun observe() {
        mViewModel.run {
//            reloadStatus.observe(viewLifecycleOwner) {
//                mBinding.reloadView.root.isVisible=it
//            }
            wea.observe(viewLifecycleOwner){
                mBinding.currentTem.setTextColor(ContextCompat.getColor(requireContext(), getWeaTextColor(it)))
            }
            emptyStatus.observe(viewLifecycleOwner) {
//                mBinding.emptyView.root.isVisible=it
                mBinding.line.isGone=it
            }
        }
    }
}