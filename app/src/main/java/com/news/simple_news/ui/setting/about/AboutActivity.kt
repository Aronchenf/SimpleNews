package com.news.simple_news.ui.setting.about

import androidx.navigation.findNavController
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityAboutBinding

class AboutActivity : BaseActivity<ActivityAboutBinding>() {
    companion object {
        fun newInstance(): AboutActivity {
            return AboutActivity()
        }
    }

    override fun initLayout(): Int = R.layout.activity_about

    override fun onSupportNavigateUp()=findNavController(R.id.nav_about_fragment).navigateUp()


}