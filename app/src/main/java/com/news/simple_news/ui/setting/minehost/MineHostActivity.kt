package com.news.simple_news.ui.setting.minehost

import android.os.Bundle
import com.news.simple_news.util.startIntentWithUrl
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityMinehostBinding

class MineHostActivity : BaseActivity<ActivityMinehostBinding>(){
    companion object{
        fun newInstance(): MineHostActivity {
            return MineHostActivity()
        }
    }
    override fun initLayout(): Int = R.layout.activity_minehost

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.mineHostBack.setOnClickListener {
            this.onBackPressed()
        }
        mBinding.mineHost.setOnClickListener {
            startIntentWithUrl(mBinding.mineHost.text.toString())
        }
    }

}