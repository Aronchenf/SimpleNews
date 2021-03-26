package com.news.simple_news.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.ui.setting.about.AboutActivity
import com.news.simple_news.ui.setting.minehost.MineHostActivity
import com.news.simple_news.ui.setting.record.WatchHistoryActivity
import com.news.simple_news.util.startActivity
import com.news.simple_news.R
import com.news.simple_news.databinding.FragmentSettingBinding
import com.news.simple_news.util.toast


class SettingFragment: BaseFragment<FragmentSettingBinding>(){

    companion object{
        fun newInstance(): SettingFragment {
            return SettingFragment()
        }
    }
    override fun initLayout(): Int = R.layout.fragment_setting

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.videoRecord.setOnClickListener {
            startActivity<WatchHistoryActivity>()
        }
        mBinding.ivAbout.setOnClickListener {
            startActivity<AboutActivity>()
        }
        mBinding.ivAvatar.setOnClickListener { turnToMineHost() }
        mBinding.tvMine.setOnClickListener { turnToMineHost() }


//        mBinding.tvMinePic.setOnClickListener { startActivity<PicActivity>() }

    }

    private fun turnToMineHost(){
        startActivity<MineHostActivity>()
    }



}