package com.news.simple_news.ui.weather

import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.adapter.WeatherIndexListAdapter
import com.news.simple_news.adapter.WeatherTodayListAdapter
import com.news.simple_news.adapter.WeatherWeekListAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.ui.MainActivity
import com.news.simple_news.util.loge
import com.news.simple_news.util.toJson
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentWeatherBinding
import com.news.simple_news.ui.weather.city.CityManagerActivity
import com.news.simple_news.util.startActivity

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    companion object {

        fun newInstance() = WeatherFragment()
    }

    private val mViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }
    private val todayListAdapter by lazy { WeatherTodayListAdapter() }
    private val weekListAdapter by lazy { WeatherWeekListAdapter() }
    private val indexListAdapter by lazy { WeatherIndexListAdapter() }

    override fun initLayout(): Int = R.layout.fragment_weather

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            todayAdapter = todayListAdapter
            weekAdapter = weekListAdapter
            indexAdapter = indexListAdapter
            viewModel = mViewModel
        }

        loge(todayListAdapter.data.toJson(),"TodayAdapter的数据")

        mBinding.swipeLayout.setOnRefreshListener {
            mViewModel.getCityData()
        }
        mBinding.scrollView.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, oldScrollY: Int ->
            (requireActivity() as MainActivity).animateBottomNavigationView(scrollY < 800 && oldScrollY > scrollY)
        }

        mBinding.toolbar.setOnMenuItemClickListener {
            startActivity<CityManagerActivity>()
            true
        }
    }

    override fun observe() {
        mViewModel.run {
            reloadStatus.observe(viewLifecycleOwner) {
                mBinding.reloadView.root.isVisible=it
            }
            emptyStatus.observe(viewLifecycleOwner) {
                mBinding.emptyView.root.isVisible=it
                mBinding.line.isGone=it
            }
        }
    }

}