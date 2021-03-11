package com.news.simple_news.ui.setting.about.content

import android.os.Bundle
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentPreBinding

class PreFragment : BaseFragment<FragmentPreBinding>() {
    companion object {
        fun newInstance() = PreFragment()
    }

    private val contentFragment by lazy { ContentFragment.newInstance() }
    override fun initLayout(): Int = R.layout.fragment_pre

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.changeContent.setOnClickListener {
            val transaction=requireActivity().supportFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left,R.anim.slide_in_left, R.anim.slide_out_right)
            if (contentFragment.isAdded){
                transaction.show(contentFragment)
            }else{
                transaction.add(R.id.ly_about,contentFragment)
            }
            transaction
                .addToBackStack(null)
                .commit()

        }
    }

    override fun onStart() {
        requireActivity().window.setWindowAnimations(R.style.about_fragment_animation)
        super.onStart()
    }
}