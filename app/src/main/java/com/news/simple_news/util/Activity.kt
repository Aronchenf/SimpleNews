package com.news.simple_news.util

import android.content.Intent
import android.net.Uri
import com.news.simple_news.ui.MainActivity

fun launchMainActivity() {
    val intent=Intent(getInstance(), MainActivity::class.java).apply {
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

fun startIntentWithUrl(url:String){
    val uri=Uri.parse(url)
    val intent=Intent(Intent.ACTION_VIEW,uri)
    getInstance().startActivity(intent)
}

inline fun <reified T> startActivity(){
    startActivity<T> {  }
}