package com.news.simple_news.ui.video.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.Item
import com.news.simple_news.model.bean.SearchHistoryBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.getString
import com.news.simple_news.R

class VideoSearchViewModel : BaseViewModel() {

    private lateinit var nextPageUrl: String

    private val _hotWordData = MutableLiveData<List<String>>()
    val hotWordData: LiveData<List<String>>
        get() = _hotWordData

    private val _searchResultList = MutableLiveData<List<Item>>()
    val searchResultList: LiveData<List<Item>>
        get() = _searchResultList

    private val _searchHistoryList = MutableLiveData<List<SearchHistoryBean>>()
    val searchHistoryList: LiveData<List<SearchHistoryBean>>
        get() = _searchHistoryList

    val resultTitle = MutableLiveData<String>()
    val resultReloadStatus=MutableLiveData<Boolean>()
    val emptyStatus=MutableLiveData<Boolean>()
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

    fun getSearchData(words: String) {
        resultReloadStatus.value=false
        launch({
            val bean = repository.getSearchResult(words)
            nextPageUrl = bean.nextPageUrl
            _searchResultList.value = bean.itemList
            resultTitle.value =
                String.format(getString(R.string.search_result_count), words, bean.total)
            emptyStatus.value=false
        },{
            resultReloadStatus.value=true
            emptyStatus.value=false
        })
    }

    fun loadMoreSearchData() {
        launch({
            val bean = repository.loadMoreData(nextPageUrl)
            nextPageUrl = bean.nextPageUrl
            val list = mutableListOf<Item>()
            list.addAll(searchResultList.value!!)
            list.addAll(bean.itemList)
            _searchResultList.value = list
        })
    }

    fun addSearchHistory(string: String) {
        val bean = SearchHistoryBean(string)
        launch({
            RoomHelper.addSearchHistory(bean)
        })
        getSearchListData()
    }

    private fun getSearchListData() {
        launch({
            val list= RoomHelper.queryAllSearchHistory()
            list.reverse()
            _searchHistoryList.value=list
        })
    }

    fun deleteAllSearch(){
        launch({
            RoomHelper.deleteAllSearch()
        })
        getSearchListData()
    }


}