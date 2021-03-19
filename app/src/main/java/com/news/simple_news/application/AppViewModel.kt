package com.news.simple_news.application

import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.util.UnPeekLiveData

class AppViewModel :BaseViewModel(){

    val mCurrentCity=UnPeekLiveData<Int>()

    fun changeCurrentCity(position:Int){
        mCurrentCity.value=position
    }

}