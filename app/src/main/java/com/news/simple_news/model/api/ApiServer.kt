package com.news.simple_news.model.api

import com.blankj.utilcode.util.JsonUtils.formatJson
import com.news.simple_news.util.logd
import com.news.simple_news.util.loge
import okhttp3.OkHttpClient
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrl
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
        .body(ResponseBody.create(mediaType, content!!))
        .build()
}


class ApiServer {
    companion object {
        private val instance by lazy {
            ApiServer()
        }

        fun getRetrofit(): ApiService {
            return instance.retrofit.create(ApiService::class.java)
        }

    }

     private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                logd(formatJson(message))
            }
        }
        setLevel(HttpLoggingInterceptor.Level.BODY)
    }

   private  val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .retryOnConnectionFailure(true)
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .build()


    private val retrofit = Retrofit.Builder()
        .baseUrl(API.NEWS_SINA)
        .callFactory(object : CustomFactory(okHttpClient) {
            override fun getNewUrl(baseUrlName: String, request: Request): HttpUrl? {
                when (baseUrlName) {
                    "weather" -> {
                        val oldUrl = request.url.toString()
                        val newUrl = oldUrl.replace(API.NEWS_SINA, API.WEATHER_HOST)
                        return newUrl.toHttpUrl()
                    }
                    "video" -> {
                        val oldUrl = request.url.toString()
                        val newUrl = oldUrl.replace(API.NEWS_SINA, API.VIDEO_URL)
                        return newUrl.toHttpUrl()
                    }
                }
                return null
            }

        })
        .addConverterFactory(GsonConverterFactory.create())
        .build()

}



