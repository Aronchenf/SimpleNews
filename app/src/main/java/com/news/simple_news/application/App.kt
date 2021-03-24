package com.news.simple_news.application

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.baidu.mapapi.CoordType
import com.news.simple_news.service.LocationService
import com.baidu.mapapi.SDKInitializer
import com.news.simple_news.util.getInstance


class App : Application(),ViewModelStoreOwner {
    lateinit var locationService: LocationService
    companion object {
        lateinit var context: Context
        lateinit var instance: App
    }

    private var mFactory: ViewModelProvider.Factory? = null
    lateinit var mAppViewModelStore: ViewModelStore

    override fun onCreate() {
        super.onCreate()
        instance =this
        context = applicationContext
        mAppViewModelStore = ViewModelStore()
        initLocation()
    }

    private fun initLocation(){
        locationService= LocationService(getInstance())
        SDKInitializer.initialize(getInstance())
        SDKInitializer.setCoordType(CoordType.BD09LL)

    }

    /**
     * 获取一个全局的ViewModel
     */
    fun getAppViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this, this.getAppFactory())
    }

    private fun getAppFactory(): ViewModelProvider.Factory {
        if (mFactory == null) {
            mFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return mFactory as ViewModelProvider.Factory
    }

    override fun getViewModelStore(): ViewModelStore =mAppViewModelStore


}