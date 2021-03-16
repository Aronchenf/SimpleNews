package com.news.simple_news.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppEventViewModel :ViewModel() {
    var addChooseCity=MutableLiveData<Boolean>()
    var changeCurrentCity=MutableLiveData<Boolean>()
}