package com.news.simple_news.work_manage

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.news.simple_news.application.AppViewModel
import com.news.simple_news.util.getInstance
import com.news.simple_news.util.loge

class WeatherWorkManager(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    private val mViewModel = getInstance().getAppViewModelProvider().get(AppViewModel::class.java)

    override fun doWork(): Result {
        loge("doWork","WeatherWorkManager")
        mViewModel.upDateAllChooseArea()
        return Result.success()
    }
}