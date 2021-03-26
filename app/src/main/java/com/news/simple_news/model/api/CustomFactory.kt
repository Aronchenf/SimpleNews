package com.news.simple_news.model.api

import okhttp3.Call
import okhttp3.HttpUrl
import okhttp3.Request

//自定义实现多BaseUrl
abstract class CustomFactory(delegate:Call.Factory) :Call.Factory{
    private val NAME_BASE_URL = "BaseUrlName"

    private var delegate:Call.Factory = delegate

    override fun newCall(request: Request): Call{
        val baseUrlName=request.header(NAME_BASE_URL)
        if (!baseUrlName.isNullOrEmpty()){
            val newHttpUrl=getNewUrl(baseUrlName,request)
            if (newHttpUrl!= null){
                val newRequest = request.newBuilder().url(newHttpUrl).build()
                return delegate.newCall(newRequest)
            }
        }
        return delegate.newCall(request)
    }

    protected abstract fun getNewUrl(baseUrlName:String,request: Request):HttpUrl?

}