package com.news.simple_news.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.telephony.TelephonyManager

object NetUtil {
    /**
     * check network is connected
     */
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return !(null == info || !info.isAvailable)
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

    /**
     * check is3G
     */
    fun is3G(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && activeNetInfo.type == ConnectivityManager.TYPE_MOBILE
    }

    /**
     * is 2G
     */
    fun is2G(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = connectivityManager.activeNetworkInfo
        return activeNetInfo != null && (activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_EDGE
                || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_GPRS || activeNetInfo.subtype == TelephonyManager.NETWORK_TYPE_CDMA)
    }

    /**
     * is wifi on
     */
    @SuppressLint("MissingPermission")
    fun isWifiEnabled(context: Context): Boolean {
        val mgrConn = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val mgrTel = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        return mgrConn.activeNetworkInfo != null && mgrConn.activeNetworkInfo!!.state == NetworkInfo.State.CONNECTED
                || mgrTel.networkType == TelephonyManager.NETWORK_TYPE_UMTS
    }
}