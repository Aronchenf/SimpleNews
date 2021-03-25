package com.news.simple_news.util

import com.google.gson.Gson
import com.google.gson.JsonParser

inline fun <reified T> String.toBean():T{
    return Gson().fromJson(this, T::class.java)
}

fun Any.toJson():String= Gson().toJson(this)


inline fun <reified T> String.toList():List<T>{
    val array=JsonParser().parse(this).asJsonArray
    val list= mutableListOf<T>()
    for (element in array){
        val bean=Gson().fromJson<T>(element, T::class.java)
        list.add(bean)
    }
    return list
}



