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
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.adapter.ViewPagerAdapter
import com.news.simple_news.databinding.FragmentWeatherBinding
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.service.LocationService
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

    private lateinit var locationService: LocationService

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
            nav().navigate(R.id.action_mainFragment_to_cityManageFragment)
//            startActivity<CityManagerActivity>()
            true
        }
    }

    private fun initLocation(){
        locationService=getInstance().locationService
        locationService.registerListener(mListener)
        locationService.setLocationOption(locationService.getDefaultLocationClientOption())
        locationService.start()
        toast("???????????????????????????......",time = longTime)
    }
    private val mListener =object : BDAbstractLocationListener(){
        override fun onReceiveLocation(location: BDLocation?) {
            if (null != location && location.locType != BDLocation.TypeServerError) {
                if (!location.district.isNullOrEmpty()){
                    locationService.stop()
                    mViewModel.addCityToDatabase(location.district)
                    toast("???????????????:${location.district}")
                    return
                }
                if (!location.city.isNullOrEmpty()){
                    locationService.stop()
                    mViewModel.addCityToDatabase(location.city)
                    toast("???????????????:${location.city}")
                    return
                }
            }
        }

        override fun onLocDiagnosticMessage(locType:Int, diagnosticType:Int, diagnosticMessage:String?) {
            super.onLocDiagnosticMessage(locType, diagnosticType, diagnosticMessage)
            when(diagnosticType){
                3->toast("?????????????????????????????????????????????")
                8->toast("?????????????????????????????????????????????????????????????????????APP????????????")
                4->toast("???????????????????????????????????????????????????")
            }
        }
    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        initLocation()
    }

    override fun onStop() {
        locationService.unregisterListener(mListener)
        locationService.stop()
        super.onStop()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStart() {
        super.onStart()
        setWorkManager()
    }

    //????????????????????????
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


    //??????videoView??????
    fun setVideoStart(wea: String? = "??????") {
        mBinding.videoView.setVideoURI(Uri.parse(getWeatherVideo(wea)))
        mBinding.videoView.setOnPreparedListener {
            //???videoView????????????????????????
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
            cityList = it
            initViewPager(it)
            setOnPageChangeCallback()
        }
        mViewModel.mChooseCityInsertResult.observe(this){
            mViewModel.getCityList()
        }

        requireActivity().getEventViewModel().changeCurrentCity.observe(this) {
            it.let {
                mBinding.viewpager.setCurrentItem(appViewModel.mCurrentCity.value!!, true)
            }
        }
        requireActivity().getEventViewModel().addChooseCity.observe(this) {
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
            fragments.add(WeatherChildFragment.newInstance(bean.city!!))
        }
        val mAdapter = ViewPagerAdapter(fragments,this)
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
        //??????????????????pageChangeListener???????????????????????????????????????????????????????????????viewpager?????????????????????listener
        //????????????listener?????????????????????????????????????????????
        mBinding.viewpager.unregisterOnPageChangeCallback(pageSelectedCallBack)
        mBinding.viewpager.registerOnPageChangeCallback(pageSelectedCallBack)
    }

    private val pageSelectedCallBack = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (cityList.isNotEmpty()) {
                setTitle(cityList[position].city!!)
                setVideoStart(cityList[position].wea)
            }
        }
    }

    private fun setTitle(title: String) {
        mBinding.toolbar.title = title
    }


}