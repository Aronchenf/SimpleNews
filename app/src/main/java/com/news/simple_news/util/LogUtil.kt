package com.news.simple_news.util

import android.util.Log
import com.news.simple_news.R

fun logd(msg: String, tag: String = getString(R.string.app_name)) {
    val strLength = msg.length
    var start = 0
    var end = 2000
    for (i in 0..99) {
        if (strLength > end) {
            Log.d(tag, msg.substring(start, end))
            start = end
            end += 2000
        } else {
            Log.d(tag, msg.substring(start, strLength))
            break
        }
    }
}

fun loge(msg: String, tag: String = getString(R.string.app_name)) {
    val strLength = msg.length
    var start = 0
    var end = 2000
    for (i in 0..99) {
        if (strLength > end) {
            Log.e(tag, msg.substring(start, end))
            start = end
            end += 2000
        } else {
            Log.e(tag, msg.substring(start, strLength))
            break
        }
    }
}