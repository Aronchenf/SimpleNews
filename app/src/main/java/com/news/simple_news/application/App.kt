package com.news.simple_news.application


import android.app.Application
import android.content.Context

class App : Application() {
    companion object {
        lateinit var context: Context
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance =this
        context = applicationContext

    }




}