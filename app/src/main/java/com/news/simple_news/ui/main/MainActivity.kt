package com.news.simple_news.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import com.blankj.utilcode.util.ActivityUtils
import com.google.android.material.animation.AnimationUtils
import com.news.simple_news.R
import com.news.simple_news.scroll.ScrollToTop
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityMainBinding
import com.news.simple_news.ui.news.NewsFragment
import com.news.simple_news.ui.setting.SettingFragment
import com.news.simple_news.ui.video.VideoFragment
import com.news.simple_news.ui.weather.WeatherFragment
import com.news.simple_news.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(){

    private lateinit var fragments: Map<Int, Fragment>

    private var bottomNavigationViewAnimator: ViewPropertyAnimator? = null
    private var currentBottomNavigationState = true

    override fun initLayout(): Int = R.layout.activity_main

    override fun initWindow() {
        overridePendingTransition(0, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {
        initFragment()
        initNavigationBar()
        if (savedInstanceState==null){
            setDefaultFragment()
        }
    }

    private fun setDefaultFragment(){
        val initialItemId=R.id.news
        mBinding.bottomView.selectedItemId=initialItemId
        showFragment(initialItemId)
    }
    private fun initFragment(){
        fragments= mapOf(
            R.id.news to NewsFragment.newInstance(),
            R.id.weather to WeatherFragment.newInstance(),
            R.id.video to VideoFragment.newInstance(),
            R.id.mine to SettingFragment.newInstance()
        )
    }

    private fun showFragment(menuItemId:Int){
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }
        val targetFragment = fragments.entries.find { it.key == menuItemId }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.container, it)
            }
        }.commit()
    }

    private fun initNavigationBar(){
        mBinding.bottomView.run {
            setOnNavigationItemSelectedListener { menuItem ->
                showFragment(menuItem.itemId)
                true
            }
            setOnNavigationItemReselectedListener { menuItem ->
                val fragment = fragments.entries.find { it.key == menuItem.itemId }?.value
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
