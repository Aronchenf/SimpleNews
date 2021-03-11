package com.news.simple_news.ui.news.child

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.news.simple_news.base.BaseViewModel
import com.news.simple_news.model.bean.NewsData

class NewsChildViewModel : BaseViewModel() {

    private val firstPage = 1
    private var page = firstPage
    private val _newsList = MutableLiveData<List<NewsData>>()
    val newsList: LiveData<List<NewsData>>
        get() = _newsList

    private val _loadMoreStatus = MutableLiveData<LoadMoreStatus>()
    val loadMoreStatus: LiveData<LoadMoreStatus>
        get() = _loadMoreStatus
    val isRefresh = ObservableBoolean(false)
    val reloadStatus = MutableLiveData<Boolean>()
    val emptyStatus=MutableLiveData<Boolean>()

    private val TYPE_ENT = "ent"
    private var type=TYPE_ENT

    fun getNewsList(type:String?) {
        isRefresh.set(true)
        this.type=type!!
        launch({
            val bean = repository.getNews(type, firstPage)
            if (bean.data.isNotEmpty()){
                emptyStatus.value=false
            }
            _newsList.value = bean.data
            page += 1
            isRefresh.set(false)
            reloadStatus.value=false
        }, {
            isRefresh.set(false)
            emptyStatus.value=false
            reloadStatus.value = page == firstPage
        })
    }

    fun loadMoreEntList() {
        _loadMoreStatus.value = LoadMoreStatus.Loading
        launch({
            val bean = repository.getNews(type, page)
            val list = mutableListOf<NewsData>().apply {
                addAll(newsList.value!!)
                addAll(bean.data)
            }
            page += 1
            _newsList.value = list
            _loadMoreStatus.value =
                if (bean.count <= 0) LoadMoreStatus.End else LoadMoreStatus.Complete
        }, { _loadMoreStatus.value = LoadMoreStatus.Fail })
    }
}