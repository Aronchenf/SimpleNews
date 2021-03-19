package com.news.simple_news.util

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.news.simple_news.application.App
import com.news.simple_news.application.AppEventViewModel
import com.news.simple_news.application.AppViewModel
import com.news.simple_news.ui.main.MainActivity

fun launchMainActivity() {
    val intent = Intent(getInstance(), MainActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    getInstance().startActivity(intent)
}

inline fun <reified T> startActivity(block: Intent.() -> Unit) {
    val intent = Intent(getInstance(), T::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.block()
    getInstance().startActivity(intent)
}

fun startIntentWithUrl(url: String) {
    val uri = Uri.parse(url)
    val intent = Intent(Intent.ACTION_VIEW, uri)
    getInstance().startActivity(intent)
}

inline fun <reified T> startActivity() {
    startActivity<T> { }
}

fun AppCompatActivity.getAppViewModel(): AppViewModel =
        getInstance().getAppViewModelProvider().get(AppViewModel::class.java)

fun Fragment.getAppViewModel(): AppViewModel = getInstance().getAppViewModelProvider().get(AppViewModel::class.java)

fun Activity.getEventViewModel(): AppEventViewModel {
    return getInstance().getAppViewModelProvider().get(AppEventViewModel::class.java)
}

fun App.getEventViewModel():AppEventViewModel{
    return getInstance().getAppViewModelProvider().get(AppEventViewModel::class.java)
}


