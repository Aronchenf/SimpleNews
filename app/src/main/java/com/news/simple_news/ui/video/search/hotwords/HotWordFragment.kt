package com.news.simple_news.ui.video.search.hotwords

import android.os.Bundle
import androidx.core.view.isGone
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.adapter.HotKeywordsAdapter
import com.news.simple_news.adapter.SearchHistoryAdapter
import com.news.simple_news.base.BaseFragment
import com.google.android.flexbox.*
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentHotWordBinding
import com.news.simple_news.util.getEventViewModel
import com.news.simple_news.util.hideSoftInput
import com.news.simple_news.util.loge
import com.news.simple_news.util.toast

class HotWordFragment : BaseFragment<FragmentHotWordBinding>() {
    companion object {
        fun newInstance() = HotWordFragment()
    }

    private val mHotWordAdapter by lazy { HotKeywordsAdapter() }
    private val mHistoryAdapter by lazy { SearchHistoryAdapter() }
    private val mViewModel by lazy { ViewModelProvider(this)[HotWordViewModel::class.java] }

    override fun initLayout(): Int = R.layout.fragment_hot_word

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.apply {
            hotWordAdapter = mHotWordAdapter
            viewModel = mViewModel
            historyAdapter = mHistoryAdapter
        }
        mBinding.icDelete.setOnClickListener {
            mViewModel.deleteAllSearch()
        }
        mBinding.ivBack.setOnClickListener { popBack() }
        mBinding.tvSearch.setOnClickListener {
            if (mBinding.etSearchView.text.toString().isEmpty()){
                toast("请输入关键词")
                return@setOnClickListener
            }
            getSearchResult()
            mBinding.etSearchView.hideSoftInput()
        }
//        mBinding.toolbar.setNavigationOnClickListener { popBack() }
//        mBinding.toolbar.setOnMenuItemClickListener {
//            if (mBinding.etSearchView.text.toString().isNotEmpty()){
//                getSearchResult()
//            }
//            mBinding.etSearchView.hideSoftInput()
//            true
//        }
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
            setEditText(mHotWordAdapter.data[position])
        }
    }

    private fun initSearchHistory() {
        val flexBoxLayoutManager = FlexboxLayoutManager(requireActivity())
        flexBoxLayoutManager.apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            alignItems = AlignItems.CENTER
            justifyContent = JustifyContent.FLEX_START
        }
        mBinding.searchHistory.layoutManager = flexBoxLayoutManager
        mHistoryAdapter.setOnItemClickListener { _, _, position ->
            setEditText(mHistoryAdapter.data[position].searchKey)
        }
    }

    override fun observe() {
        mViewModel.searchHistoryList.observe(this) {
            mBinding.history.isGone = it.isNullOrEmpty()
            mBinding.icDelete.isGone = it.isNullOrEmpty()
        }

        requireActivity().getEventViewModel().addSearchKey.observe(this) {
            it.let { mViewModel.getSearchListData() }
        }

        requireActivity().getEventViewModel().lastSearchKey.observe(this){
            setEditText(it)
        }
    }

    private fun setEditText(key: String) {
        mBinding.etSearchView.setText(key)
        mBinding.etSearchView.setSelection(key.length)
    }

    //转到结果界面
    private fun getSearchResult() {
        val searchKey = mBinding.etSearchView.text.toString()
        val bundle = HotWordFragmentArgs.Builder().setSearchKey(searchKey).build().toBundle()
        nav().navigate(R.id.action_hotWordFragment_to_resultFragment, bundle)
    }

}