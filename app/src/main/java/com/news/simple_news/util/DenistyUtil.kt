package com.news.simple_news.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager
import com.news.simple_news.application.App

fun Context.scale() = resources.displayMetrics.density

fun Context.fontScale() = resources.displayMetrics.scaledDensity

fun Context.dip2px(dpValue: Float): Int = (dpValue * scale() + 0.5f).toInt()

fun Context.px2dip(pxValue: Float): Int = (pxValue / scale() + 0.5f).toInt()

fun Context.px2sp(pxValue: Float): Int = (pxValue / fontScale() + 0.5f).toInt()

fun Context.sp2px(spValue: Float): Int = (spValue * fontScale() + 0.5f).toInt()

/**
 * 获取屏幕宽度
 */
@Suppress("DEPRECATION")
fun getScreenWidth(): Int {
    val wm= getInstance().getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm= DisplayMetrics()
    getInstance().resources.configuration.densityDpi
    wm.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

/**
 * 获取屏幕高度
 */
@Suppress("DEPRECATION")
fun getScreenHeight(): Int {
    val wm= App.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val dm=DisplayMetrics()
    wm.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}

/**
 * 获取屏幕DPI
 */
fun getScreenDpi(): Int = getInstance().resources.displayMetrics.densityDpi

/**
 * 根据手机的分辨率从dp的单位转成为px（像素）
 */
fun dip2px(dpValue: Float): Int = getInstance().dip2px(dpValue)

/**
 * 根据手机的分辨率从px（像素）的单位转成为dp
 */
fun px2dip(pxValue: Float): Int = getInstance().px2dip(pxValue)

/**
 * 将px值转换为sp值，保证文字大小不变
 */
fun px2sp(pxValue: Float): Int = getInstance().px2sp(pxValue)

/**
 * 将sp值转换为px值，保证文字大小不变
 */
fun sp2px(spValue: Float): Int = getInstance().sp2px(spValue)
