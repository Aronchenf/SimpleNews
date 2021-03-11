package com.news.simple_news.ui.setting.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.Data
import com.news.simple_news.model.room.RoomHelper

class HistoryViewModel : BaseViewModel() {

    private val _historyList = MutableLiveData<List<Data>>()
    val historyList: LiveData<List<Data>>
        get() = _historyList

    init {
        getHistoryList()
    }
    private fun getHistoryList(){
        val listDeffer=async { RoomHelper.queryAllWatch() }
        launch({
            val list=listDeffer.await()
            _historyList.value=list
        })
    }

    fun deleteSingleRecord(uid:Long){
        launch({
            RoomHelper.deleteWatch(uid)
        })
        getHistoryList()
    }

    fun deleteAllRecord(){
        launch({
            RoomHelper.deleteAllWatch()
        })
        getHistoryList()
    }
}