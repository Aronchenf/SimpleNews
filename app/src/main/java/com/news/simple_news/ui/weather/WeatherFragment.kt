package com.news.simple_news.ui.weather

import android.graphics.Color
import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.adapter.ViewPagerAdapter
import com.news.simple_news.databinding.FragmentWeatherBinding
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.ui.weather.child.WeatherChildFragment
import com.news.simple_news.util.*
import com.news.simple_news.work_manage.WeatherWorkManager
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle
import java.util.concurrent.TimeUnit

class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    companion object {
        fun newInstance() = WeatherFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().window.setFormat(PixelFormat.TRANSLUCENT)
    }

    private val mViewModel by lazy { ViewModelProvider(this)[WeatherViewModel::class.java] }

    private lateinit var cityList: List<CityManageBean>
    override fun initLayout(): Int = R.layout.fragment_weather

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.toolbar.setOnMenuItemClickListener {
            startActivity<CityManagerActivity>()
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        setWorkManager()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresDeviceIdle(true)
            .build()
        val updateWeatherWorkRequest =
            PeriodicWorkRequestBuilder<WeatherWorkManager>(7, TimeUnit.DAYS)
                .setConstraints(constraints).build()
        WorkManager.getInstance(getInstance()).enqueue(updateWeatherWorkRequest)
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
            if (it.isEmpty()) {
                startActivity<CityManagerActivity>()
            }
            cityList = it
            initViewPager(it)
            setOnPageChangeCallback()
        }
        requireActivity().getEventViewModel().changeCurrentCity.observe(this) {
            it.let {
                mBinding.viewpager.setCurrentItem(appViewModel.mCurrentCity.value!!, true)
            }
        }
        requireActivity().getEventViewModel().addCity.observe(this) {
            it.let {
                mViewModel.getCityList()
            }
        }
        requireActivity().getEventViewModel().deleteCity.observe(this) {
            it.let {
                mViewModel.getCityList()
            }
        }
    }

    private fun initViewPager(list: List<CityManageBean>) {
        val fragments = mutableListOf<Fragment>()
        for (bean in list) {
            fragments.add(WeatherChildFragment.newInstance(bean.city))
        }
        val mAdapter = ViewPagerAdapter(requireActivity(), fragments)
        mBinding.viewpager.apply {
            adapter = mAdapter
            offscreenPageLimit = 2
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
        //本质上是添加pageChangeListener，当添加了新的城市之后，城市列表扩大，但是viewpager还是会使用旧的listener
        //所以先把listener移除，再添加，避免列表溢出问题
        mBinding.viewpager.unregisterOnPageChangeCallback(pageSelectedCallBack)
        mBinding.viewpager.registerOnPageChangeCallback(pageSelectedCallBack)
    }

    private val pageSelectedCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (cityList.isNotEmpty()) {
                setTitle(cityList[position].city)
                setVideoStart(cityList[position].wea)
            }
        }
    }

    private fun setTitle(title: String) {
        mBinding.toolbar.title = title
    }

}