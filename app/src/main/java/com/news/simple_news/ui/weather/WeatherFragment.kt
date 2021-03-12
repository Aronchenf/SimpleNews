package com.news.simple_news.ui.weather

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.adapter.ViewPagerAdapter
import com.news.simple_news.databinding.FragmentWeatherBinding
import com.news.simple_news.ui.weather.child.WeatherChildFragment
import com.news.simple_news.ui.weather.citymanage.CityManagerActivity
import com.news.simple_news.util.getWeatherVideo
import com.news.simple_news.util.loge
import com.news.simple_news.util.startActivity
import com.news.simple_news.util.toJson

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private val mViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    override fun initLayout(): Int = R.layout.fragment_weather

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.swipeLayout.setOnRefreshListener {
            mViewModel.getCityData()
        }
        mBinding.toolbar.setOnMenuItemClickListener {
            startActivity<CityManagerActivity>()
            true
        }

    }

//    private fun setVideoStart(wea: String?) {
//        mBinding.videoView.setVideoURI(Uri.parse(getWeatherVideo(wea)))
//        mBinding.videoView.start()
//    }

    private val fragments = mutableListOf<Fragment>()

    override fun onResume() {
        mViewModel.getCityList()
        super.onResume()
    }

    override fun observe() {
        mViewModel.cityList.observe(viewLifecycleOwner) {
            for (i in it.indices) {
                fragments.add(WeatherChildFragment.newInstance(it[i].data!!))
            }
            val mAdapter = ViewPagerAdapter(requireActivity(), fragments)
            mBinding.viewpager.apply {
                adapter = mAdapter
                currentItem = 0
            }
            mBinding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    mBinding.title.text = it[position].city
                    loge(it[position].wea.toString())
//                    setVideoStart(it[position].wea)
                }
            })
        }
    }


}