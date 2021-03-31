package com.news.simple_news.ui.main

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewPropertyAnimator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var fragments: Map<Int, Fragment>

    private var bottomNavigationViewAnimator: ViewPropertyAnimator? = null
    private var currentBottomNavigationState = true

    override fun initLayout(): Int = R.layout.activity_main

    override fun initWindow() {
        overridePendingTransition(0, 0)
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()
        ActivityUtils.finishOtherActivities(javaClass)
    }

//    private var mExitTime: Long = 0
//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
//                ActivityUtils.finishActivity(this)
//                finish()
//            } else {
//                mExitTime = System.currentTimeMillis()
//                toast("再按一次退出程序")
//            }
//            return true
//        }
//        return super.onKeyDown(keyCode, event)
//    }

}
