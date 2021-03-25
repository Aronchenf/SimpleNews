package com.news.simple_news.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityWelcomeBinding
import com.news.simple_news.util.launchMainActivity
import com.permissionx.guolindev.PermissionX

class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    override fun initLayout(): Int = R.layout.activity_welcome

    private val animation by lazy { AnimationUtils.loadAnimation(this, R.anim.image_xml) }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun initView(savedInstanceState: Bundle?) {
        initPermission()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun initPermission() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            ).onExplainRequestReason { scope, deniedList ->
                scope.showRequestReasonDialog(
                    deniedList,
                    "该应用将会使用到以下这些权限",
                    "确定",
                    "取消"
                )
            }
            .onForwardToSettings { scope, deniedList ->
                scope.showForwardToSettingsDialog(
                    deniedList,
                    "您需要在设置中手动打开这些权限",
                    "确定",
                    "取消"
                )
            }
            .request { allGranted, _, _ ->
                if (allGranted){
                    mBinding.img.startAnimation(animation)
                    animation.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationRepeat(p0: Animation?) {}
                        override fun onAnimationEnd(p0: Animation?) {
                            launchMainActivity()
                            overridePendingTransition(0, 0)
                        }

                        override fun onAnimationStart(p0: Animation?) {}
                    })
                }else{
                    finish()
                }
            }
    }


}