package com.news.simple_news.ui.video.search.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.news.simple_news.R
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.Item
import com.news.simple_news.model.bean.SearchHistoryBean
import com.news.simple_news.model.room.RoomHelper
import com.news.simple_news.util.getString

class ResultViewModel : BaseViewModel() {

    private val _searchResultList = MutableLiveData<List<Item>>()
    val searchResultList: LiveData<List<Item>>
        get() = _searchResultList

    private lateinit var nextPageUrl: String
    val resultTitle = MutableLiveData<String>()
    val resultReloadStatus = MutableLiveData<Boolean>()
    val emptyStatus = MutableLiveData<Boolean>()

    val addKeyStatus = MutableLiveData<Long>()
    fun getSearchData(words: String) {
        resultReloadStatus.value = false
        launch({
            val bean = repository.getSearchResult(words)
            nextPageUrl = bean.nextPageUrl
            _searchResultList.value = bean.itemList
            resultTitle.value =
                String.format(getString(R.string.search_result_count), words, bean.total)
            emptyStatus.value = false
        }, {
            resultReloadStatus.value = true
            emptyStatus.value = false
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
            addKeyStatus.value = RoomHelper.addSearchHistory(bean)
        })
    }
}