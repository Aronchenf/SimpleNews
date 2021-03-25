package com.news.simple_news.application

import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel

class AppViewModel :BaseViewModel(){

    val mCurrentCity=MutableLiveData<Int>()

    fun changeCurrentCity(position:Int){
        mCurrentCity.value=position
    }

}