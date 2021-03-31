package com.news.simple_news.ui.news.child

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.news.simple_news.adapter.NewsAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentNewsChildBinding
import com.news.simple_news.scroll.ScrollToTop
import com.news.simple_news.ui.main.MainActivity
import com.news.simple_news.ui.main.MainFragment
import com.news.simple_news.ui.main.MainFragmentArgs
import com.news.simple_news.ui.main.MainFragmentDirections
import com.news.simple_news.ui.news.NewsFragment
import com.news.simple_news.util.loge

@Suppress("LABEL_NAME_CLASH")
class NewsChildFragment : BaseFragment<FragmentNewsChildBinding>(), ScrollToTop {

    companion object {
        const val TYPE = "type"
        fun newInstance(type: String): NewsChildFragment {
            return NewsChildFragment().apply {
                arguments = Bundle().apply {
                    putString(TYPE, type)
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
        val layoutManager = mBinding.rvNews.layoutManager as LinearLayoutManager
        mBinding.rvNews.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val firstItem = layoutManager.findFirstVisibleItemPosition()

                ((parentFragment as NewsFragment).parentFragment as MainFragment).animateBottomNavigationView(
                    firstItem < 8 || dy < 0
                )
            }
        })
        
        mAdapter.setOnItemClickListener { _, view, position ->
            val bean=mAdapter.getItem(position)
            val bundle=MainFragmentArgs.Builder().setWebLink(bean.link).build().toBundle()
            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_newsDetailFragment,bundle)
        }
    }

    override fun lazyLoadData() {
        val type = arguments?.getString(TYPE)
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
            mBinding.emptyView.root.isVisible = it
        }
    }

    override fun scrollToTop() {
        mBinding.rvNews.smoothScrollToPosition(0)
    }

}