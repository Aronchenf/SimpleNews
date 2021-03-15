package com.news.simple_news.ui.video

import android.annotation.SuppressLint
import com.news.simple_news.base.BaseFragment
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.news.simple_news.adapter.video.VideoAdapter
import com.news.simple_news.ui.MainActivity
import com.news.simple_news.ui.video.search.VideoSearchActivity
import com.news.simple_news.util.startActivity
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentVideoBinding
import java.text.SimpleDateFormat
import java.util.*

class VideoFragment : BaseFragment<FragmentVideoBinding>() {

    companion object {
        fun newInstance() = VideoFragment()
    }

    private val videoAdapter by lazy { VideoAdapter() }
    private val mViewModel by lazy { ViewModelProvider(this)[VideoViewModel::class.java] }
    private val simpleDateFormat by lazy {
        SimpleDateFormat("- MMM. dd, 'Brunch' -", Locale.ENGLISH)
    }

    override fun initLayout(): Int = R.layout.fragment_video

    @SuppressLint("NewApi")
    override fun initView(savedInstanceState: Bundle?) {
        mBinding.apply {
            adapter = videoAdapter
            viewModel = mViewModel
        }

        mBinding.videoRefresh.setOnRefreshListener { mViewModel.getData() }

        mBinding.rvVideo.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = mBinding.rvVideo.layoutManager as LinearLayoutManager
                val currentVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (currentVisibleItemPosition == 0) {
                    //背景设置为透明
                    mBinding.run {
                        toolbarVideo.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.color_translucent
                            )
                        )
                        ivSearch.setImageResource(R.drawable.ic_search__white)
                        tvHeaderTitle.text = ""
                    }
                } else {
                    mBinding.run {
                        toolbarVideo.setBackgroundColor(
                            ContextCompat.getColor(
                                requireActivity(),
                                R.color.color_title_bg
                            )
                        )
                    }

                    mBinding.ivSearch.setImageResource(R.drawable.ic_search_black)
                    val item =
                        videoAdapter.data[currentVisibleItemPosition].item
                    if (item?.type == "textHeader") {
                        mBinding.tvHeaderTitle.text = item.data.text
                    } else {
                        val date = Date(item?.data?.date!!)
                        mBinding.tvHeaderTitle.text = simpleDateFormat.format(date)
                    }
                }
            }

        })

        mBinding.rvVideo.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
            val layoutManager = mBinding.rvVideo.layoutManager as LinearLayoutManager
            val currentVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
//            (requireActivity() as MainActivity).animateBottomNavigationView(
//                currentVisibleItemPosition <= 3 || oldScrollY > scrollY
//            )
        }

        mBinding.ivSearch.setOnClickListener { startActivity<VideoSearchActivity>() }

    }

    override fun observe() {
        mViewModel.run {
            emptyStatus.observe(viewLifecycleOwner) {
                mBinding.emptyView.root.isVisible = it
            }
            reloadStatus.observe(viewLifecycleOwner) {
                mBinding.reloadView.root.isVisible = it
            }
        }
    }


}