package com.news.simple_news.ui.setting.record

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.news.simple_news.adapter.WatchHistoryAdapter
import com.news.simple_news.ui.video.detail.VideoDetailActivity
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentWatchHistoryBinding

class WatchHistoryFragment : BaseFragment<FragmentWatchHistoryBinding>() {
    override fun initLayout(): Int = R.layout.fragment_watch_history

//    private var itemData = ArrayList<VideoBean.Issue.Item>()

    private val mAdapter by lazy { WatchHistoryAdapter() }
    private val mViewModel by lazy { ViewModelProvider(this)[HistoryViewModel::class.java] }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.historyAdapter = mAdapter
        mBinding.viewModel = mViewModel
        mBinding.tvClear.setOnClickListener {
            mViewModel.deleteAllRecord()
        }
        mBinding.toolbar.setNavigationOnClickListener { popBack() }
        mAdapter.setOnItemLongClickListener { _, _, position ->
            val data = mAdapter.data[position]
            mViewModel.deleteSingleRecord(data.id)
            mAdapter.removeAt(position)
            mAdapter.notifyItemRemoved(position)
            true
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            VideoDetailActivity.gotoVideoPlayer(requireActivity(), mAdapter.data[position])
        }
    }

}