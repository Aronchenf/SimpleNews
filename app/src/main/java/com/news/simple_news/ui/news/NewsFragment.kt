package com.news.simple_news.ui.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.news.simple_news.adapter.ViewPagerAdapter
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.ui.news.child.NewsChildFragment
import com.google.android.material.tabs.TabLayoutMediator
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentNewsBinding


class NewsFragment : BaseFragment<FragmentNewsBinding>(){
    companion object {
        const val TYPE_ENT = "ent"
        const val TYPE_FASHION = "fashion"
        const val TYPE_SCIENCE = "tech"
        const val TYPE_SPORTS = "sports"
        fun newInstance(): NewsFragment {
            return NewsFragment()
        }
    }

    override fun initLayout(): Int = R.layout.fragment_news

    override fun initView(savedInstanceState: Bundle?) {
        val titles = listOf("娱乐", "时尚", "体育", "科技")
        val fragments = listOf<Fragment>(
            NewsChildFragment.newInstance(TYPE_ENT),
            NewsChildFragment.newInstance(TYPE_FASHION),
            NewsChildFragment.newInstance(TYPE_SCIENCE),
            NewsChildFragment.newInstance(TYPE_SPORTS),
        )

        val mAdapter = ViewPagerAdapter(requireActivity(), fragments)
        setSlop()
        mBinding.viewpager.apply {
            adapter = mAdapter
            currentItem = 0
        }

        TabLayoutMediator(mBinding.tabLayout, mBinding.viewpager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    //设置ViewPager2滑动灵敏度
    private fun setSlop(){
        try {
            val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
            recyclerViewField.isAccessible = true
            val recyclerView = recyclerViewField.get(mBinding.viewpager)
            val touchSlopField= RecyclerView::class.java.getDeclaredField("mTouchSlop")
            touchSlopField.isAccessible = true
            val touchSlop = touchSlopField[recyclerView] as Int
            touchSlopField.set(
                recyclerView,
                touchSlop * 4
            ) //6 is empirical value
        } catch (ignore: Exception) {
            ignore.printStackTrace()
        }
    }


}