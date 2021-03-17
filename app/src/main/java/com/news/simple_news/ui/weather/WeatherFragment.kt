package com.news.simple_news.ui.weather

import android.graphics.Color
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.jeremyliao.liveeventbus.LiveEventBus
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.adapter.ViewPagerAdapter
import com.news.simple_news.databinding.FragmentWeatherBinding
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.ui.weather.child.WeatherChildFragment
import com.news.simple_news.util.*
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    private val mViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    private lateinit var cityList:List<CityManageBean>
    override fun initLayout(): Int = R.layout.fragment_weather

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.toolbar.setOnMenuItemClickListener {
            startActivity<CityManagerActivity>()
            true
        }
        mBinding.viewpager.currentItem=0
    }

    fun setVideoStart(wea: String? = "多云") {
        mBinding.videoView.setVideoURI(Uri.parse(getWeatherVideo(wea)))
        mBinding.videoView.setOnPreparedListener {
            //将videoView的背景设置为透明
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

    override fun observe() {
        mViewModel.cityList.observe(viewLifecycleOwner) {
            cityList=it
            initViewPager(it)
            setOnPageChangeCallback()
        }
        LiveEventBus.get("refresh", Int::class.java).observe(this,
            Observer<Int> { t ->
                loge("我已经接受到了来自Manage的消息$t")
                mBinding.viewpager.setCurrentItem(t-1,true)
                mViewModel.getCityList()
            })
    }

    private fun initViewPager(list: List<CityManageBean>) {
        val fragments = mutableListOf<Fragment>()
        for (bean in list) {
            fragments.add(WeatherChildFragment.newInstance(bean.city))
        }
        val mAdapter = ViewPagerAdapter(requireActivity(), fragments)
        mBinding.viewpager.apply {
            adapter = mAdapter
            offscreenPageLimit = list.size
        }
        mBinding.indicator.setSliderColor(
            getColor(R.color.gray),
            getColor(R.color.dim_gray)
        )
            .setSliderWidth(resources.getDimension(R.dimen.dp_8))
            .setSliderHeight(resources.getDimension(R.dimen.dp_8))
            .setSlideMode(IndicatorSlideMode.WORM)
            .setIndicatorStyle(IndicatorStyle.CIRCLE)
            .setupWithViewPager(mBinding.viewpager)
    }

    private fun setOnPageChangeCallback() {
        mBinding.viewpager.unregisterOnPageChangeCallback(pageSelectedCallBack)
        mBinding.viewpager.registerOnPageChangeCallback(pageSelectedCallBack)
    }

    private val pageSelectedCallBack=object :ViewPager2.OnPageChangeCallback(){
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (cityList.isNotEmpty()){
                setTitle(cityList[position].city)
                setVideoStart(cityList[position].wea)
            }
        }
    }

    private fun setTitle(title: String) {
        mBinding.toolbar.title = title
    }


}