package com.news.simple_news.application

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppEventViewModel : ViewModel() {
    val addChooseCity = MutableLiveData<Boolean>()
    val changeCurrentCity = MutableLiveData<Boolean>()
    val deleteCity = MutableLiveData<Boolean>()
    val addSearchKey = MutableLiveData<Boolean>()
    val lastSearchKey=MutableLiveData<String>()
}