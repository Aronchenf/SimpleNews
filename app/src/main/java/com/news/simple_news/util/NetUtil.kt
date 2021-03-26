package com.news.simple_news.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object NetUtil {
    /**
     * check network is connected
     */
    fun isConnected(): Boolean {
        val connectivityManager = getInstance().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT>=23){
            val networkCapabilities=connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (networkCapabilities!=null){
                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            }
        }else{
            val info = connectivityManager.activeNetworkInfo
            if (info!=null){
                return info.isConnected
            }
        }
        return false
    }

    /**
     * isWifi
     */
    fun isWifi(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_WIFI
    }
}