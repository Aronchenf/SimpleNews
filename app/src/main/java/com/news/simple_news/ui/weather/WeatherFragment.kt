package com.news.simple_news.ui.weather

import android.graphics.Color
import android.media.MediaParser
import android.media.MediaPlayer
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
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.ui.weather.child.WeatherChildFragment
import com.news.simple_news.ui.weather.citymanage.CityManagerActivity
import com.news.simple_news.util.getColor
import com.news.simple_news.util.getWeatherVideo
import com.news.simple_news.util.loge
import com.news.simple_news.util.startActivity
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private val mViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    override fun initLayout(): Int = R.layout.fragment_weather

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.swipeLayout.setOnRefreshListener {
            mViewModel.getCityList()
        }
        mBinding.toolbar.setOnMenuItemClickListener {
            startActivity<CityManagerActivity>()
            true
        }
        mBinding.indicator.setSliderColor(
            getColor(R.color.half_gray),
            getColor(R.color.blue)
        )
            .setSliderWidth(resources.getDimension(R.dimen.dp_8))
            .setSliderHeight(resources.getDimension(R.dimen.dp_8))
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setupWithViewPager(mBinding.viewpager)
    }

    private fun setVideoStart(wea: String?) {
        mBinding.videoView.setVideoURI(Uri.parse(getWeatherVideo(wea)))
        mBinding.videoView.setOnPreparedListener {
            it.setOnInfoListener { _, what, _ ->
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    mBinding.videoView.setBackgroundColor(Color.TRANSPARENT)
                }
                true
            }
            it.start()
            it.isLooping = true
        }

    }

    override fun onResume() {
        mViewModel.getCityList()
        super.onResume()
    }

    private val fragments = mutableListOf<Fragment>()
    override fun observe() {
        mViewModel.cityList.observe(viewLifecycleOwner) {
            initViewPager(it)
            setOnPageChangeCallback(it)
        }
    }


    private fun initViewPager(list: List<CityManageBean>) {
        for (bean in list) {
            fragments.add(WeatherChildFragment.newInstance(bean.data!!))
        }
        val mAdapter = ViewPagerAdapter(requireActivity(), fragments)
        mBinding.viewpager.apply {
            adapter = mAdapter
            currentItem = 0
        }
    }

    private fun setOnPageChangeCallback(list: List<CityManageBean>) {
        mBinding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (list.isNotEmpty()) {
                    setTitle(list[position].city)
                    setVideoStart(list[position].wea)
                }
            }
        })
    }

    private fun setTitle(title: String) {
        mBinding.title.text = title
    }


}