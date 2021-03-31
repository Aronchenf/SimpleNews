package com.news.simple_news.ui.setting.about

import android.os.Bundle
import com.news.simple_news.R
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentAboutBinding

class AboutFragment : BaseFragment<FragmentAboutBinding>() {
    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun initLayout(): Int = R.layout.fragment_about



}