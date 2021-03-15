package com.news.simple_news.model.api

import com.news.simple_news.util.formatJson
import com.news.simple_news.util.logd
import com.news.simple_news.util.loge
import okhttp3.OkHttpClient
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * response 拦截器
 */
private val interceptor = Interceptor { chain ->
    val request = chain.request()
    val startTime = System.currentTimeMillis()
    loge("----------------------Request Start-----------------------------")
    loge("|Request:${request.url}")
    val response = chain.proceed(chain.request())
    val endTime = System.currentTimeMillis()
    val duration = endTime - startTime
    val mediaType = response.body?.contentType()
    val content = response.body?.string()
    logd("|Response:${formatJson(content)}")
    loge("----------------------Request End:$duration 毫秒----------------------")

    response.newBuilder()
        .addHeader("Accept-Encoding","")
        .body(ResponseBody.create(mediaType, content!!))
        .build()
}

private val httpLoggingInterceptor =HttpLoggingInterceptor().apply {
    object :HttpLoggingInterceptor.Logger{
        override fun log(message: String) {
            logd(formatJson(message))
        }
    }
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor(httpLoggingInterceptor)
    .retryOnConnectionFailure(true)
    .connectTimeout(60L, TimeUnit.SECONDS)
    .readTimeout(60L, TimeUnit.SECONDS)
    .writeTimeout(60L, TimeUnit.SECONDS)
    .build()

fun getRetrofit(url: String): ApiService {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}



