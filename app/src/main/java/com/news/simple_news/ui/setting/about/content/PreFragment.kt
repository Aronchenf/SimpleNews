package com.news.simple_news.ui.setting.about.content

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentPreBinding

class PreFragment : BaseFragment<FragmentPreBinding>() {
    companion object {
        fun newInstance() = PreFragment()
    }

    override fun initLayout(): Int = R.layout.fragment_pre

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.changeContent.setOnClickListener {
            findNavController().navigate(R.id.action_preFragment_to_contentFragment)
        }
    }

}