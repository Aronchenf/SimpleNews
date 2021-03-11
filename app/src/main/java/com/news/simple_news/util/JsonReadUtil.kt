package com.news.simple_news.util

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Reader
import java.lang.StringBuilder

class JsonReadUtil {
    companion object{
        fun getJsonStr(context: Context,fileName:String):String{
            val stringBuilder=StringBuilder()
            val assetManager=context.assets
            try {
                val input=assetManager.open(fileName)
                val br=BufferedReader(InputStreamReader(input) as Reader)
                var str:String?=null
                while ({str = br.readLine();str}()!=null){
                    stringBuilder.append(str)
                }
            }catch (e:IOException){
                e.printStackTrace()
            }
            return stringBuilder.toString()
        }
    }
}