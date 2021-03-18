package com.news.simple_news.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.news.simple_news.util.getAppViewModel


abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {

    val appViewModel by lazy { getAppViewModel() }
    protected lateinit var mBinding: DB

    //懒加载
    private var lazyLoaded = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, initLayout(), container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        observe()

//        getBaseActicity()?.fragmentAnimator = DefaultHorizontalAnimator()
    }

    open fun initLayout() = 0
    open fun initView(savedInstanceState: Bundle?) {}
    open fun observe() {}
    open fun lazyLoadData() {}

    override fun onResume() {
        super.onResume()
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

}