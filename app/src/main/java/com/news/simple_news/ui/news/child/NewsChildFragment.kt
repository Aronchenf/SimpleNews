package com.news.simple_news.ui.news.child

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.news.simple_news.adapter.NewsAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentNewsChildBinding

@Suppress("LABEL_NAME_CLASH")
class NewsChildFragment : BaseFragment<FragmentNewsChildBinding>() {
    companion object {
        const val TYPE="type"
        fun newInstance(type:String) : NewsChildFragment {
            return NewsChildFragment().apply {
                arguments=Bundle().apply {
                    putString(TYPE,type)
                }
            }
        }
    }

    private val mViewModel by lazy { ViewModelProvider(this)[NewsChildViewModel::class.java] }

    private val mAdapter by lazy { NewsAdapter() }

    override fun initLayout(): Int = R.layout.fragment_news_child

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)

        mBinding.viewModel = mViewModel
        mBinding.adapter = mAdapter

    }

    override fun lazyLoadData() {
        super.lazyLoadData()
        val type=arguments?.getString(TYPE)
        mViewModel.getNewsList(type)
        mBinding.run {
            swipLayout.setOnRefreshListener { mViewModel.getNewsList(type) }
            reloadView.btnReload.setOnClickListener {
                mViewModel.getNewsList(type)
            }
            rvNews.requestDisallowInterceptTouchEvent(true)
        }
    }

    override fun observe() {
        mViewModel.loadMoreStatus.observe(viewLifecycleOwner) {
            when (it) {
                LoadMoreStatus.Complete -> mAdapter.loadMoreModule.loadMoreComplete()
                LoadMoreStatus.Fail -> mAdapter.loadMoreModule.loadMoreFail()
                LoadMoreStatus.End -> mAdapter.loadMoreModule.loadMoreEnd()
                else -> return@observe
            }
        }
        mViewModel.reloadStatus.observe(viewLifecycleOwner) {
            mBinding.reloadView.root.isVisible = it
        }
        mViewModel.emptyStatus.observe(viewLifecycleOwner) {
            mBinding.emptyView.root.isVisible=it
        }
    }

}