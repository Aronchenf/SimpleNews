package com.news.simple_news.ui


import android.os.Bundle
import android.view.animation.*
import com.news.simple_news.util.launchMainActivity
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityWelcomeBinding


class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>(){


    override fun initLayout(): Int = R.layout.activity_welcome

    override fun initView(savedInstanceState: Bundle?) {
        val animation= AnimationUtils.loadAnimation(this, R.anim.image_xml)
        mBinding.img.startAnimation(animation)
        animation?.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {}

            override fun onAnimationEnd(p0: Animation?) {
                launchMainActivity()
                overridePendingTransition(0,0)
            }

            override fun onAnimationStart(p0: Animation?) {}

        })
    }


}