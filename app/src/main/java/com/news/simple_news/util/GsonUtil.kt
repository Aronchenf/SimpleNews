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

fun formatJson(jsonString: String?):String{
    if (jsonString.isNullOrEmpty())return ""
    val sb=StringBuilder()
    var last: Char
    var current = '\u0000'
    var indent = 0
    for (i in jsonString.indices){
        last=current
        current=jsonString[i]
        when(current){
            '{','['->{
                sb.append(current).append('\n')
                indent++
                addIndentBlank(sb,indent)
            }
            '}',']'->{
                sb.append('\n')
                indent--
                addIndentBlank(sb,indent)
                sb.append(current)
            }
            ','->{
                sb.append(current)
                if (last!='\\'){
                    sb.append('\n')
                    addIndentBlank(sb,indent)
                }
            }
            else->sb.append(current)
        }
    }
    return sb.toString()
}

fun addIndentBlank(sb:StringBuilder,indent:Int){
    for (i in 0 until indent){
        sb.append('\t')
    }
}
