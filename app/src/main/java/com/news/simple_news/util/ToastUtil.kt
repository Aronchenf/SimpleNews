package com.news.simple_news.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

 val shortTime = Toast.LENGTH_SHORT
 val longTime = Toast.LENGTH_LONG

fun Fragment.toast(message: CharSequence, time: Int = shortTime)=
    Toast.makeText(requireActivity(),message,time).show()

fun toast(message: CharSequence, time: Int = shortTime, context: Context = getInstance()) =
    Toast.makeText(context, message, time).show()

fun toast(@StringRes resId: Int, time: Int = shortTime, context: Context = getInstance()) =
    Toast.makeText(
        context,
        resId, time
    ).show()