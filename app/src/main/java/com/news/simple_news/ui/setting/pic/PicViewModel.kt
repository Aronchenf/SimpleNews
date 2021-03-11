package com.news.simple_news.ui.setting.pic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel

class PicViewModel : BaseViewModel() {

    private val _picUrl=MutableLiveData<String>()
    val picUrl:LiveData<String>
    get() = _picUrl

    init {
        getPic()
    }

    fun getPic(){
        launch({
            val bean=repository.requestPic()
            _picUrl.value="https://${bean.img}"
        })
    }
}