package com.news.simple_news.ui.video.search.result

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.adapter.VideoSearchResultAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.ui.video.detail.VideoDetailActivity
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentResultBinding
import com.news.simple_news.ui.video.search.hotwords.HotWordFragmentArgs
import com.news.simple_news.util.getEventViewModel
import com.news.simple_news.util.hideSoftInput


class ResultFragment : BaseFragment<FragmentResultBinding>() {
    companion object {
        fun newInstance() = ResultFragment()
    }
    private lateinit var words:String
    private val mResultAdapter by lazy { VideoSearchResultAdapter() }
    private val mViewModel by lazy { ViewModelProvider(this)[ResultViewModel::class.java] }
    override fun initLayout(): Int = R.layout.fragment_result
    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.apply {
            searchResultAdapter = mResultAdapter
            viewModel = mViewModel
            ivBack.setOnClickListener { popBack() }
        }
        //搜索
        mBinding.tvSearch.setOnClickListener {
            mBinding.etSearchView.hideSoftInput()
            mViewModel.getSearchData(getInputData())
            mViewModel.addSearchHistory(getInputData())
        }
        mBinding.reloadView.btnReload.setOnClickListener {
            mViewModel.getSearchData(getInputData())
        }
        mBinding.etSearchView.doAfterTextChanged {
            requireActivity().getEventViewModel().lastSearchKey.postValue(it.toString())
        }
        initResult()
    }

    override fun onResume() {
        super.onResume()
        words= arguments?.let { HotWordFragmentArgs.fromBundle(it).searchKey }.toString()//获取传递来的关键词
        mViewModel.getSearchData(words)
        setEditText(words)
        mViewModel.addSearchHistory(words)
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

    private fun getInputData()=mBinding.etSearchView.text.toString()

    private fun setEditText(key: String) {
        mBinding.etSearchView.setText(key)
        mBinding.etSearchView.setSelection(key.length)
    }

    override fun observe() {
        mViewModel.run {
            resultReloadStatus.observe(viewLifecycleOwner) {
                mBinding.reloadView.root.isVisible = it
            }
            searchResultList.observe(viewLifecycleOwner) {
                mBinding.emptyView.root.isVisible = it.isNullOrEmpty()
            }
            addKeyStatus.observe(viewLifecycleOwner){
                requireActivity().getEventViewModel().addSearchKey.postValue(true)
            }
        }
    }

}