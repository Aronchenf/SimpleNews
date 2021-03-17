package com.news.simple_news.application


import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.jeremyliao.liveeventbus.LiveEventBus

class App : Application() {
    companion object {
        lateinit var context: Context
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance =this
        context = applicationContext
        initEventBus()
    }

     private fun initEventBus(){
         LiveEventBus
             .config()
             .lifecycleObserverAlwaysActive(true)
             .autoClear(true)
     }



}