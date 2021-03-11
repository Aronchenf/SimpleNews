package com.news.simple_news.ui.setting.about

import android.os.Bundle
import com.news.simple_news.ui.setting.about.content.ContentFragment
import com.news.simple_news.ui.setting.about.content.PreFragment
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity<ActivityAboutBinding>() {
    companion object {
        fun newInstance(): AboutActivity {
            return AboutActivity()
        }
    }

    private val preFragment by lazy { PreFragment.newInstance() }
    private val contentFragment by lazy { ContentFragment.newInstance() }
    override fun initLayout(): Int = R.layout.activity_about

    override fun initView(savedInstanceState: Bundle?) {
        loadFragment()
    }

    private fun loadFragment(){
        supportFragmentManager.beginTransaction()
            .add(R.id.ly_about,preFragment)
            .commit()
    }

    override fun onBackPressed() {
        if (contentFragment.isVisible){
            supportFragmentManager.beginTransaction().remove(contentFragment).commit()
        }else{
            super.onBackPressed()
        }
    }



}