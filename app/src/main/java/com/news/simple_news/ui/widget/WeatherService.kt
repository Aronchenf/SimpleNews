package com.news.simple_news.ui.widget

import android.app.Service
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.IBinder
import android.widget.RemoteViews
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.news.simple_news.R
import com.news.simple_news.model.api.API
import com.news.simple_news.model.api.ApiService
import com.news.simple_news.model.bean.WeatherBean
import com.news.simple_news.service.LocationService
import com.news.simple_news.util.getInstance
import com.news.simple_news.util.getWeatherImages
import com.news.simple_news.util.returnCityName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherService : Service() {

    private var locationService: LocationService? = null
    private var locationCityName: String? = null

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * 首次创建服务时，系统将调用此方法来执行一次性设置程序（在调用 onStartCommand() 或 onBind() 之前）。
     * 如果服务已在运行，则不会调用此方法。该方法只被调用一次
     */
    override fun onCreate() {
        super.onCreate()
        getLocationCity()
    }

    /**
     * 每次通过startService方法启动service时都会被回调
     */
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        locationService?.unregisterListener(mListener)
        locationService?.stop()
    }

    private fun getLocationCity() {
        initLocation()//获取定位
        getWeatherData()
    }

    private fun getWeatherData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(API.WEATHER_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(ApiService::class.java)
        val call = service.getLocationCityWeather(
            API.weatherType,
            returnCityName(locationCityName.toString()), API.appId, API.appSecret
        )
        call.enqueue(object : Callback<WeatherBean> {
            override fun onFailure(call: Call<WeatherBean>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<WeatherBean>, response: Response<WeatherBean>) {
                val weatherBean = response.body()
                val dataBean = weatherBean?.data!![0]
                val weatherViews = RemoteViews(packageName, R.layout.layout_widget)
                weatherViews.setImageViewResource(
                    R.id.iv_weather,
                    getWeatherImages(dataBean.wea_day)
                )
                weatherViews.setTextViewText(R.id.tv_tem, dataBean.tem)
                weatherViews.setTextViewText(R.id.tv_city, weatherBean.city)
                val manager = AppWidgetManager.getInstance(getInstance())
                val componentName = ComponentName(getInstance(), WeatherWidgetProvider::class.java)
                manager.updateAppWidget(componentName, weatherViews)
            }
        })
    }

    private fun initLocation() {
        locationService = getInstance().locationService
        locationService?.registerListener(mListener)
        locationService?.setLocationOption(locationService?.getDefaultLocationClientOption())
        locationService?.start()
    }

    private val mListener = object : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation?) {
            if (null != location && location.locType != BDLocation.TypeServerError) {
                if (!location.district.isNullOrEmpty()) {
                    locationService?.stop()
                    locationCityName = location.district
                    return
                }
                if (!location.city.isNullOrEmpty()) {
                    locationService?.stop()
                    locationCityName = location.city
                    return
                }
            }
        }
    }


}