package com.news.simple_news.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun formatDateMsByMS(milliseconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("mm:ss")
    return simpleDateFormat.format(Date(milliseconds))
}
@SuppressLint("SimpleDateFormat")
fun formatDateMsByYMD(milliseconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    return simpleDateFormat.format(Date(milliseconds))
}
@SuppressLint("SimpleDateFormat")
fun formatDateMsByYMDHM(milliseconds: Long): String {
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    return simpleDateFormat.format(Date(milliseconds))
}