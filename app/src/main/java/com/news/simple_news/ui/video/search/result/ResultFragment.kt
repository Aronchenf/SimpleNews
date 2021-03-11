package com.news.simple_news.ui.video.search.result

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.adapter.VideoSearchResultAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.ui.video.detail.VideoDetailActivity
import com.news.simple_news.ui.video.search.VideoSearchViewModel
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentResultBinding


class ResultFragment : BaseFragment<FragmentResultBinding>() {
    companion object {
        fun newInstance() = ResultFragment()
    }
    private lateinit var words:String
    private val mResultAdapter by lazy { VideoSearchResultAdapter() }
    private val mViewModel by lazy { ViewModelProvider(requireActivity())[VideoSearchViewModel::class.java] }
    override fun initLayout(): Int = R.layout.fragment_result
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.apply {
            searchResultAdapter = mResultAdapter
            viewModel = mViewModel
        }
        mBinding.reloadView.btnReload.setOnClickListener {
            mViewModel.getSearchData(words)
        }
        initResult()
    }

    private fun initResult() {
        mResultAdapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMoreSearchData()
        }
        mResultAdapter.setOnItemClickListener { _, _, position ->
            VideoDetailActivity.gotoVideoPlayer(
                requireActivity(),
                mResultAdapter.data[position].data
            )
        }
    }

    fun getList(key: String) {
        words=key
        mViewModel.getSearchData(key)
    }

    override fun observe() {
        mViewModel.run {
            resultReloadStatus.observe(viewLifecycleOwner) {
                mBinding.reloadView.root.isVisible = it
            }
            searchResultList.observe(viewLifecycleOwner) {
                mBinding.emptyView.root.isVisible = it.isNullOrEmpty()
            }
        }
    }
}