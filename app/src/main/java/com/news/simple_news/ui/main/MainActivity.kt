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
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.Poi
import com.blankj.utilcode.util.ActivityUtils
import com.google.android.material.animation.AnimationUtils
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityMainBinding
import com.news.simple_news.service.LocationService
import com.news.simple_news.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(){

    private lateinit var fragments: Map<Int, Fragment>

    private lateinit var locationService: LocationService
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
        initBottomView()
        mBinding.bottomView.setupWithNavController(
            navGraphIds, supportFragmentManager,
            R.id.nav_main_fragment
        )
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_main_fragment).navigateUp()
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
        super.onDestroy()

    }

    override fun onStart() {
        super.onStart()
        ActivityUtils.finishOtherActivities(javaClass)
        initLocation()
    }

    override fun onStop() {
        locationService.unregisterListener(mListener)
        locationService.stop()
        super.onStop()
    }

    private fun initLocation(){
        locationService=getInstance().locationService
        locationService.registerListener(mListener)
        locationService.setLocationOption(locationService.getDefaultLocationClientOption())
        locationService.start()
    }
    private val mListener =object :BDAbstractLocationListener(){
        override fun onReceiveLocation(location: BDLocation?) {
            if (null != location && location.locType != BDLocation.TypeServerError) {
                if (!location.district.isNullOrEmpty()){
                    locationService.stop()
                    loge(location.district,"区")
                    return
                }
                if (!location.city.isNullOrEmpty()){
                    locationService.stop()
                    loge(location.city,"市")
                    return
                }
            }
        }
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
