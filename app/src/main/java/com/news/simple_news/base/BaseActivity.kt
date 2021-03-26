package com.news.simple_news.base

import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.news.simple_news.R
import com.news.simple_news.util.getAppViewModel

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var mBinding: DB

    val appViewModel by lazy { getAppViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWindow()
//        //避免在状态栏 的显示状态发生变化时重新布局，从而避免界面卡顿。
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
//        //将window扩展至全屏，也就是全屏显示，并且不会覆盖状态栏。如果这一句就实现效果了，那么为什么还要添加
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        initViewBinding()
        initView(savedInstanceState)
        observe()
    }

    private fun initViewBinding() {
        mBinding = DataBindingUtil.setContentView(this, initLayout())
        mBinding.lifecycleOwner = this
    }

    open fun initWindow() {
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    open fun initLayout() = 0
    open fun initView(savedInstanceState: Bundle?) {}
    open fun observe() {}

    override fun onBackPressed() {
        finishAfterTransition()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        super.onBackPressed()
    }


}