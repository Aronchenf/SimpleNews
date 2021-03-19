package com.news.simple_news.base

import android.os.Bundle
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

    override fun onStart() {
        super.onStart()
        initWindow()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
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
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }


}