package com.news.simple_news.ui.setting.pic

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.news.simple_news.util.gone
import com.news.simple_news.util.setBackImage
import com.news.simple_news.util.visible
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityPicBinding

class PicActivity : BaseActivity<ActivityPicBinding>(){

    private val handler = Handler()

    companion object {

        const val PERMISSIONS_REQUEST_CODE = 0
    }

    var url: String? = null

    private var isGone = false
    override fun initLayout(): Int = R.layout.activity_pic

    private val mViewModel by lazy { ViewModelProvider(this)[PicViewModel::class.java] }

    @SuppressLint("RestrictedApi")
    override fun initView(savedInstanceState: Bundle?) {

        mBinding.toolBack.setOnClickListener { this.onBackPressed() }
        if (!isGone) {
            handler.postDelayed({
                mBinding.picTool.gone()
            }, 2000)
            isGone = true
        }
        mBinding.flPic.setOnClickListener {
            isGone = if (isGone) {
                mBinding.picTool.visible()
                false
            } else {
                mBinding.picTool.gone()
                true
            }


        }

//        float_savepic.setOnClickListener { url?.let { mPresenter!!.getPic(it) } }
//
//        float_setwall.setOnClickListener { url?.let { mPresenter!!.getWall(it) } }
    }

    override fun observe() {
        mViewModel.picUrl.observe(this) {
            mBinding.ivPic.setBackImage(it)
        }
    }

//    override fun setWallPaper(responseBody: ResponseBody) {
//        setWall(responseBody)
////        toast("设置壁纸成功")
//    }
//
//    override fun savePic(responseBody: ResponseBody) {
//        savePicture(responseBody)
////        toast("下载成功")
//    }

}