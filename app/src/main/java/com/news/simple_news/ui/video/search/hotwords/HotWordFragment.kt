package com.news.simple_news.ui.video.search.hotwords


import android.os.Bundle
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.adapter.HotKeywordsAdapter
import com.news.simple_news.adapter.SearchHistoryAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.ui.video.search.VideoSearchActivity
import com.news.simple_news.ui.video.search.VideoSearchViewModel
import com.google.android.flexbox.*
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentHotWordBinding


class HotWordFragment : BaseFragment<FragmentHotWordBinding>() {
    companion object {
        fun newInstance() = HotWordFragment()
    }

    private val mHotWordAdapter by lazy { HotKeywordsAdapter() }
    private val mHistoryAdapter by lazy { SearchHistoryAdapter() }
    private val mViewModel by lazy { ViewModelProvider(requireActivity())[VideoSearchViewModel::class.java] }

    override fun initLayout(): Int = R.layout.fragment_hot_word

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.apply {
            hotWordAdapter = mHotWordAdapter
            viewModel = mViewModel
            historyAdapter=mHistoryAdapter
        }
        mBinding.icDelete.setOnClickListener {
            mViewModel.deleteAllSearch()
        }
        initHotWord()
        initSearchHistory()
    }

    private fun initHotWord() {
        val flexBoxLayoutManager = FlexboxLayoutManager(requireActivity())
        flexBoxLayoutManager.apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.CENTER
            justifyContent = JustifyContent.FLEX_START
        }
        mBinding.mRecyclerViewHot.layoutManager = flexBoxLayoutManager
        mHotWordAdapter.setOnItemClickListener { _, _, position ->
            (activity as? VideoSearchActivity)?.setEditText(mHotWordAdapter.data[position])
        }

    }

    private fun initSearchHistory(){
        val flexBoxLayoutManager = FlexboxLayoutManager(requireActivity())
        flexBoxLayoutManager.apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.CENTER
            justifyContent = JustifyContent.FLEX_START
        }
        mBinding.searchHistory.layoutManager = flexBoxLayoutManager
    }

    override fun observe() {
        mViewModel.run {
            searchHistoryList.observe(viewLifecycleOwner) {
                mBinding.history.isGone = it.isNullOrEmpty()
                mBinding.icDelete.isGone=it.isNullOrEmpty()
            }
        }
    }

    fun addSearchHistory(string: String) {
        mViewModel.addSearchHistory(string)
    }

}