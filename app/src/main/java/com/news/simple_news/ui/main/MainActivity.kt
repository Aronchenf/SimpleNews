package com.news.simple_news.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import com.amap.api.location.AMapLocationListener
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult
import com.blankj.utilcode.util.ActivityUtils
import com.google.android.material.animation.AnimationUtils
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityMainBinding
import com.news.simple_news.model.bean.Place
import com.news.simple_news.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), GeocodeSearch.OnGeocodeSearchListener{

    private lateinit var fragments: Map<Int, Fragment>

    private val locationClient by lazy { ALocationFactory.createLocationClient(
        getInstance(),
        ALocationFactory.createOnceOption(),
        locationListener
    ) }

    private val geocodeSearch by lazy { GeocodeSearch(getInstance()) }
    private val viewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    private val navGraphIds by lazy {
        listOf(
            R.navigation.news,
            R.navigation.weather,
            R.navigation.video,
            R.navigation.mine
        )
    }
    private var bottomNavigationViewAnimator: ViewPropertyAnimator? = null
    private var currentBottomNavigationState = true

    override fun initLayout(): Int = R.layout.activity_main

    override fun initWindow() {
        overridePendingTransition(0, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {

        geocodeSearch.setOnGeocodeSearchListener(this)
        initLocation()
        initBottomView()
        mBinding.bottomView.setupWithNavController(
            navGraphIds, supportFragmentManager,
            R.id.nav_main_fragment
        )
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_main_fragment).navigateUp()
    }

    private fun initLocation() {
        stopLocation()
        startLocation()
    }

    private val locationListener = AMapLocationListener { location ->
        if (location != null) {
            if (location.errorCode == 0) {
                reGeoCoder(location.latitude, location.longitude)
            } else {
                toast("定位失败,请检查网络")
            }
        }
    }
    private fun reGeoCoder(lat: Double, lng: Double) {
        val query = RegeocodeQuery(LatLonPoint(lat, lng), 200f, GeocodeSearch.AMAP)
        geocodeSearch.getFromLocationAsyn(query)
    }

    private fun startLocation() {
        locationClient.startLocation()
    }

    private fun stopLocation() {
        locationClient.stopLocation()
    }

    override fun onRegeocodeSearched(result: RegeocodeResult?, code: Int) {
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (result?.regeocodeAddress != null && result.regeocodeAddress.formatAddress != null) {
                val place=result.regeocodeAddress.district.substring(0,result.regeocodeAddress.district.length-1)
                loge(place,"LocationAddress")
                toast(place)
                viewModel.addCityToDatabase(place)
            }
        }
    }
    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {
    }


    override fun observe() {
        viewModel.mChooseCityInsertResult.observe(this) {
            it.let {
                getEventViewModel().addCity.postValue(true)
            }
        }
    }

    //初始化BottomView
    private fun initBottomView() {
        mBinding.bottomView.run {
            setOnNavigationItemReselectedListener { item ->
                val fragment = fragments.entries.find { it.key == item.itemId }?.value
                if (fragment is ScrollToTop) {
                    fragment.scrollToTop()
                }
            }
        }
    }

    //BottomView动画
    fun animateBottomNavigationView(show: Boolean) {
        if (currentBottomNavigationState == show) {
            return
        }
        if (bottomNavigationViewAnimator != null) {
            bottomNavigationViewAnimator?.cancel()
            mBinding.bottomView.clearAnimation()
        }
        currentBottomNavigationState = show
        val targetY = if (show) 0F else mBinding.bottomView.measuredHeight.toFloat()
        val duration = if (show) 225L else 175L
        bottomNavigationViewAnimator = mBinding.bottomView.animate()
            .translationY(targetY)
            .setDuration(duration)
            .setInterpolator(AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    bottomNavigationViewAnimator = null
                }
            })
    }

    override fun onDestroy() {
        bottomNavigationViewAnimator?.cancel()
        mBinding.bottomView.clearAnimation()
        bottomNavigationViewAnimator = null
        stopLocation()
        super.onDestroy()
        locationClient.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        ActivityUtils.finishOtherActivities(javaClass)
    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                ActivityUtils.finishActivity(this)
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                toast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
