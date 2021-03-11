package com.news.simple_news.ui.video.detail

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.transition.Transition
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.bumptech.glide.Glide
import com.news.simple_news.adapter.TransitionListenerAdapter
import com.news.simple_news.adapter.videodetail.VideoDetailAdapter
import com.news.simple_news.model.bean.Data
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityVideodetailBinding
import com.news.simple_news.util.getString
import com.news.simple_news.util.visible
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack
import com.shuyu.gsyvideoplayer.utils.OrientationUtils

@SuppressLint("SimpleDateFormat")
class VideoDetailActivity : BaseActivity<ActivityVideodetailBinding>() {

    companion object {
        const val BUNDLE_VIDEO_DATA = "video_data"
        const val TRANSITION = "TRANSITION"

        /**
         * 跳转到视频详情页面播放
         */
        fun gotoVideoPlayer(activity: Activity, view: View, itemData: Data) {
            val intent = Intent(activity, VideoDetailActivity::class.java).apply {
                putExtra(BUNDLE_VIDEO_DATA, itemData)
                putExtra(TRANSITION, true)
            }
            val activityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, getString(R.string.transition_video_img))
            ActivityCompat.startActivity(activity, intent, activityOptions.toBundle())

        }

        fun gotoVideoPlayer(activity: Activity, itemData: Data) {
            val intent = Intent(activity, VideoDetailActivity::class.java).apply {
                putExtra(BUNDLE_VIDEO_DATA, itemData)
                putExtra(TRANSITION, true)
            }
            ActivityCompat.startActivity(activity, intent, null)
        }
    }

    private val mAdapter by lazy { VideoDetailAdapter() }
    private val mViewModel by lazy { ViewModelProvider(this)[VideoDetailViewModel::class.java] }

    private var orientationUtils: OrientationUtils? = null

    private var isPlay: Boolean = false
    private var isPause: Boolean = false

    private var mTransition: Transition? = null
    override fun initLayout(): Int = R.layout.activity_videodetail

    override fun initWindow() {
        window.setWindowAnimations(R.style.video)
    }

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.apply {
            viewModel = mViewModel
            adapter = mAdapter
        }

//        //过度动画
//        initTransition()

        val itemData = intent.getSerializableExtra(BUNDLE_VIDEO_DATA) as Data
        mViewModel.setItemInfo(itemData)

        //设置相关视频Item的点击事件
        mAdapter.setOnItemClickListener { _, _, position ->
            //防止点击详情介绍之后重新加载
            if (position > 0) {
                mViewModel.setItemInfo(mAdapter.data[position].data)
            }
        }
        /***下拉刷新***/
        mBinding.mRefreshLayout.setOnRefreshListener { mViewModel.loadVideoInfo() }
    }

    /**
     * 初始化VideoView的配置
     */
    private fun initVideoViewConfig(itemData: Data) {
        //设置旋转
        orientationUtils = OrientationUtils(this, mBinding.mVideoView)
        //初始化不打开外部的旋转
        orientationUtils?.isEnable = false

        //增加封面
        val imageView = ImageView(this)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this)
            .load(itemData.cover.feed)
            .centerCrop()
            .into(imageView)

        //设置全屏按键功能。
        GSYVideoOptionBuilder().apply {
            setThumbImageView(imageView)
            setIsTouchWiget(true)
            setRotateViewAuto(false)
            setLockLand(false)
            setAutoFullWithSize(false)
            setShowFullAnimation(false)
            setNeedLockFull(true)
            setVideoAllCallBack(object : GSYSampleCallBack() {
                override fun onPrepared(url: String?, vararg objects: Any?) {
                    super.onPrepared(url, *objects)
                    //开始播放了才能旋转和全屏
                    orientationUtils?.isEnable = true
                    isPlay = true
                }

                override fun onQuitFullscreen(url: String?, vararg objects: Any?) {
                    super.onQuitFullscreen(url, *objects)
                    if (orientationUtils != null) {
                        orientationUtils?.backToProtVideo()
                    }
                }
            })
            setLockClickListener { _, lock ->
                if (orientationUtils != null) {
                    orientationUtils?.isEnable = !lock
                }
            }
                .build(mBinding.mVideoView)
        }
        mBinding.mVideoView.apply {
            //是否旋转
            isRotateViewAuto = true
            //设置返回键
            backButton.visible()
            //是否可以滑动调整
            setIsTouchWiget(true)
            //设置返回键功能
            backButton.setOnClickListener { this@VideoDetailActivity.onBackPressed() }
        }
        mBinding.mVideoView.fullscreenButton.setOnClickListener {
            //直接横屏
            orientationUtils?.resolveByClick()
            mBinding.mVideoView.startWindowFullscreen(this@VideoDetailActivity, true, true)
        }
    }

    private fun initTransition() {
        //延迟动画执行
        postponeEnterTransition()
        //设置共用元素对应的View
        ViewCompat.setTransitionName(mBinding.mVideoView, getString(R.string.transition_video_img))
        //获取共享元素进入转场对象
        mTransition = window.sharedElementEnterTransition
        mTransition?.addListener(object : TransitionListenerAdapter() {
            override fun onTransitionEnd(transition: Transition) {
                mViewModel.loadVideoInfo()
                mTransition?.removeListener(this)
            }
        })
        //开始动画执行
        startPostponedEnterTransition()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (isPlay && !isPause) {
            mBinding.mVideoView.onConfigurationChanged(
                this,
                newConfig,
                orientationUtils,
                true,
                true
            )
        }
    }

    override fun observe() {
        mViewModel.run {
            itemData.observe(this@VideoDetailActivity) {
                initVideoViewConfig(it)

            }
            videoUrl.observe(this@VideoDetailActivity) {
                mBinding.mVideoView.setUp(it, false, "")
                //开始自动播放
                mBinding.mVideoView.startPlayLogic()
            }

        }
    }

    override fun onPause() {
        super.onPause()
       mBinding. mVideoView.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        mBinding.mVideoView.onVideoResume()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (orientationUtils != null) {
            orientationUtils?.releaseListener()
        }
    }

    override fun onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils?.backToProtVideo()
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return
        }

        super.onBackPressed()
        //先返回正常状态
//        if (orientationUtils?.screenType == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
//            orientationUtils?.screenType=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
//            mVideoView.fullscreenButton.performClick()
//            return
//        }
//
////        //释放所有
        mBinding.mVideoView.setVideoAllCallBack(null)
        GSYVideoManager.releaseAllVideos()
        finish()
        overridePendingTransition(0,R.anim.video_exit)
    }
}

