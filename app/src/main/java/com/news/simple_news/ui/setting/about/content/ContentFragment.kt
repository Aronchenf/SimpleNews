package com.news.simple_news.ui.setting.about.content

import android.graphics.Typeface
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentAboutContentBinding
import com.news.simple_news.util.getSystemAssets

class ContentFragment : BaseFragment<FragmentAboutContentBinding>() {
    companion object {
        fun newInstance(): ContentFragment {
            return ContentFragment()
        }
    }

    override fun initLayout(): Int = R.layout.fragment_about_content

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.backAbout.setOnClickListener {
            findNavController().popBackStack()
        }

        val tf1=Typeface.createFromAsset(getSystemAssets(),"fonts/SIMLI.TTF")
        mBinding.run {
            text1.typeface = tf1
            text2.typeface = tf1
            text3.typeface = tf1
        }
    }

}