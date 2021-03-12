package com.news.simple_news.ui.video.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.ViewModelProvider
import com.news.simple_news.util.hideSoftInput
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityVideoSearchBinding
import com.news.simple_news.ui.video.search.hotwords.HotWordFragment
import com.news.simple_news.ui.video.search.result.ResultFragment

class VideoSearchActivity : BaseActivity<ActivityVideoSearchBinding>() {


    private val mViewModel by lazy { ViewModelProvider(this)[VideoSearchViewModel::class.java] }

    private val hotFragment by lazy { HotWordFragment.newInstance() }
    private val resultFragment by lazy { ResultFragment.newInstance() }

    override fun initLayout(): Int = R.layout.activity_video_search

    override fun initView(savedInstanceState: Bundle?) {
        loadHotWordsView()
        mBinding.run {
            viewModel = mViewModel
            etSearchBack.setOnClickListener { onBackPress() }
            tvSearch.setOnClickListener {
                val words = etSearchView.text.toString()
                if (words.isEmpty()) return@setOnClickListener
                it.hideSoftInput()
                hotFragment.addSearchHistory(words)
                resultFragment.getList(words)
                showResultFragment()
            }
            etSearchView.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    tvSearch.performClick()
                    true
                } else {
                    false
                }
            }
        }
    }

    private fun loadHotWordsView() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container_video, hotFragment)
            .add(R.id.container_video, resultFragment)
            .show(hotFragment)
            .hide(resultFragment)
            .commit()
    }

    //显示搜索结果
    private fun showResultFragment() {
        supportFragmentManager.beginTransaction().show(resultFragment).commit()
    }

    //隐藏搜索结果
    private fun hideResultFragment() {
        supportFragmentManager.beginTransaction().hide(resultFragment).commit()
    }

    private fun onBackPress() {
        if (resultFragment.isVisible) {
            hideResultFragment()
        } else {
            finish()
        }
    }

    override fun onBackPressed() {
        onBackPress()
    }

    fun setEditText(words: String) {
        //设置搜索框文字
        mBinding.etSearchView.setText(words)
        mBinding.etSearchView.setSelection(words.length)
    }

}