package com.news.simple_news.ui.video.search.hotwords

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.SearchHistoryBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.loge

class HotWordViewModel :BaseViewModel() {

    private val _hotWordData = MutableLiveData<List<String>>()
    val hotWordData: LiveData<List<String>>
        get() = _hotWordData

    private val _searchHistoryList = MutableLiveData<List<SearchHistoryBean>>()
    val searchHistoryList: LiveData<List<SearchHistoryBean>>
        get() = _searchHistoryList

    init {
        getHotWordsData()
        getSearchListData()
    }
    private fun getHotWordsData() {
        launch({
            val list = repository.requestHotWordData()
            _hotWordData.value = list
        })
    }

      fun getSearchListData() {
         loge("getSearchListData","HotWordViewModel")
        launch({
            val list= RoomHelper.queryAllSearchHistory()
            list.reverse()
            _searchHistoryList.value=list
        })
    }

    fun deleteAllSearch(){
        loge("deleteAllSearch","HotWordViewModel")
        launch({
            RoomHelper.deleteAllSearch()
            getSearchListData()
        })
    }

}