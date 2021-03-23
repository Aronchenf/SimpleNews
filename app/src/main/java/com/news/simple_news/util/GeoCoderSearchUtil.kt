package com.news.simple_news.util

import android.content.Context
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.RegeocodeResult

class GeoCoderSearchUtil private constructor(context: Context) :
    GeocodeSearch.OnGeocodeSearchListener {

    private val geocodeSearch by lazy { GeocodeSearch(context) }
    private var geoCoderAddressListener: GeoCoderAddressListener? = null

    companion object {
        private var geoCoderSearchUtil: GeoCoderSearchUtil? = null
        fun getInstance(context: Context): GeoCoderSearchUtil {
            if (geoCoderSearchUtil == null) {
                geoCoderSearchUtil = GeoCoderSearchUtil(context)
            }
            return geoCoderSearchUtil!!
        }
    }

    override fun onRegeocodeSearched(result: RegeocodeResult?, code: Int) {
        if (code == AMapException.CODE_AMAP_SUCCESS) {
            if (result?.regeocodeAddress != null && result.regeocodeAddress.formatAddress != null) {
                val city = result.regeocodeAddress.district
                geoCoderAddressListener?.onAddressResult(city)
            }
        }
    }

    fun setAddressListener(geoCoderAddressListener: GeoCoderAddressListener) {
        this.geoCoderAddressListener = geoCoderAddressListener
    }

    override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {}

    fun reGeoCoder(lat: Double, lng: Double) {
        val query = RegeocodeQuery(LatLonPoint(lat, lng), 200f, GeocodeSearch.AMAP)
        geocodeSearch.getFromLocationAsyn(query)
    }

    interface GeoCoderAddressListener {
        fun onAddressResult(result: String)
    }


}