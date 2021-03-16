package com.news.simple_news.ui.weather

import android.graphics.Color
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
import com.news.simple_news.util.*
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
        mBinding.toolbar.setOnMenuItemClickListener {
            startActivity<CityManagerActivity>()
            true
        }
    }
     fun setVideoStart(wea: String?="多云") {
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
            for(bean in it){
                loge(it.size.toString(),"现在List的长度为")
                loge(bean.city,"WeatherFragment")
            }
            setTitle(it[it.size-1].city)
            initViewPager(it)
            setOnPageChangeCallback(it)
        }
        requireActivity().getEventViewModel().addChooseCity.observe(this){
            it?.let {
                mViewModel.getCityList()
            }

        }
        requireActivity().getEventViewModel().changeCurrentCity.observe(this){
            it?.let {
                mBinding.viewpager.setCurrentItem(appViewModel.mCurrentCity.value!!,true)
            }
        }
    }

    private fun initViewPager(list: List<CityManageBean>) {
        val fragments = mutableListOf<Fragment>()
        for (bean in list) {
            loge(bean.city,"已存入的城市有")
            fragments.add(WeatherChildFragment.newInstance(bean.city))
        }
        val mAdapter = ViewPagerAdapter(requireActivity(), fragments)
        mBinding.viewpager.apply {
            adapter = mAdapter
            currentItem = 0
            offscreenPageLimit=list.size
        }
        mBinding.indicator.setSliderColor(
                getColor(R.color.gray),
                getColor(R.color.dim_gray))
                .setSliderWidth(resources.getDimension(R.dimen.dp_8))
                .setSliderHeight(resources.getDimension(R.dimen.dp_8))
                .setSlideMode(IndicatorSlideMode.WORM)
                .setIndicatorStyle(IndicatorStyle.CIRCLE)
                .setupWithViewPager(mBinding.viewpager)
    }

    private fun setOnPageChangeCallback(list: List<CityManageBean>) {
        mBinding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (list.isNotEmpty()) {
                    loge(list.size.toString(),"List的长度为")
                    for(bean in list){
                        loge(bean.city,"OnPageChangeCallback")
                    }
                    setTitle(list[position].city)
                    setVideoStart(list[position].wea)
                }
            }
        })
    }

    private fun setTitle(title: String) {
        mBinding.toolbar.title = title
    }


}