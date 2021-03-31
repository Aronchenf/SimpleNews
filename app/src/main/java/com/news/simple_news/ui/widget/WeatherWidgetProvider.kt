package com.news.simple_news.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import com.news.simple_news.R
import com.news.simple_news.ui.WelcomeActivity
import com.news.simple_news.util.getInstance

class WeatherWidgetProvider : AppWidgetProvider() {

    /*
     * 每次窗口小部件被更新都调用一次该方法
     */
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        for (appWidgetId in appWidgetIds!!) {
            val views = RemoteViews(context?.packageName, R.layout.layout_widget)
            val manager = AppWidgetManager.getInstance(getInstance())
            val componentName = ComponentName(getInstance(), WeatherWidgetProvider::class.java)
            manager?.updateAppWidget(componentName, views)
        }
    }

    /*
    * 接收窗口小部件点击时发送的广播
    */
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val layout=RemoteViews(getInstance().packageName,R.layout.layout_widget)
        val pendingIntent = PendingIntent.getActivity(
            getInstance(),
            0,
            Intent(getInstance(), WelcomeActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        layout.setOnClickPendingIntent(R.id.widget_layout,pendingIntent)
        val manager = AppWidgetManager.getInstance(getInstance())
        val componentName = ComponentName(getInstance(), WeatherWidgetProvider::class.java)
        manager?.updateAppWidget(componentName, layout)
    }

    /*
    * 当小部件从备份恢复时调用该方法
    */
    override fun onRestored(context: Context?, oldWidgetIds: IntArray?, newWidgetIds: IntArray?) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    /*
       * 每删除一次窗口小部件就调用一次
       */
    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
    }

    /*
       * 当该窗口小部件第一次添加到桌面时调用该方法
       */
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        context?.startService(Intent(context, WeatherService::class.java))
    }

    /*
     * 当最后一个该窗口小部件删除时调用该方法
     */
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
        context?.stopService(Intent(context, WeatherService::class.java))
    }

    /*
      * 当小部件大小改变时
      */
    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
}