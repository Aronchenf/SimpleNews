package com.news.simple_news.util

import android.content.Context
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

//AMapLocation配置
object ALocationFactory {

    fun createDefaultOption(): AMapLocationClientOption {
        return AMapLocationClientOption().apply {
            //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
            interval = 1000
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            httpTimeOut = 10000
            //设置是否返回地址信息（默认返回地址信息）
            isNeedAddress = true
            //设置是否gps优先，只在高精度模式下有效。默认关闭
            isGpsFirst = true
            //设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
            geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT
            //定位协议，目前支持二种定位协议
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTPS)
        }
    }

    fun createOnceOption(): AMapLocationClientOption {
        return AMapLocationClientOption().apply {
            //获取到最新鲜的wifi列表
            isWifiScan=true
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //获取一次定位结果
            isOnceLocation = true
            //获取最近3s内精度最高的一次定位结果:
            //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。
            // 如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
            isOnceLocationLatest = true
            //设置是否返回地址信息（默认返回地址信息）
            isNeedAddress = true
            //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
            httpTimeOut = 5000
            //设置是否gps优先，只在高精度模式下有效。默认关闭
            isGpsFirst = true
            //设置逆地理信息的语言，默认值为默认语言（根据所在地区选择语言）
            geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT
            //定位协议，目前支持二种定位协议
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)
        }
    }

    fun createLocationClient(
        context: Context,
        option: AMapLocationClientOption,
        listener: AMapLocationListener
    ): AMapLocationClient {
        return AMapLocationClient(context).apply {
            setLocationOption(option)
            setLocationListener(listener)
        }
    }

}