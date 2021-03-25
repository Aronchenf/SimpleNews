package com.news.simple_news.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.news.simple_news.adapter.video.VideoAdapter
import com.news.simple_news.adapter.videodetail.VideoDetailAdapter
import com.news.simple_news.ui.news.child.NewsChildViewModel
import com.news.simple_news.ui.video.VideoType
import com.news.simple_news.ui.video.VideoViewModel
import com.news.simple_news.adapter.*
import com.news.simple_news.model.bean.*

@BindingAdapter("newsData")
fun getNewsData(recyclerView: RecyclerView, list: List<NewsData>?) {
    (recyclerView.adapter as? NewsAdapter)?.setList(list)
}

@BindingAdapter("entData")
fun loadMoreEntData(recyclerView: RecyclerView, viewModel: NewsChildViewModel) {
    (recyclerView.adapter as? NewsAdapter)?.loadMoreModule?.setOnLoadMoreListener {
        viewModel.loadMoreEntList()
    }
}

@BindingAdapter("weatherTodayList")
fun weatherTodayList(recyclerView: RecyclerView, list: List<HoursBean>?) {
    (recyclerView.adapter as? WeatherTodayListAdapter)?.setList(list)
}

@BindingAdapter("weatherWeekList")
fun weatherWeekList(recyclerView: RecyclerView, list: List<WeatherDataBean>?) {
    (recyclerView.adapter as? WeatherWeekListAdapter)?.setList(list)
}

@BindingAdapter("weatherIndexList")
fun weatherIndexList(recyclerView: RecyclerView,list: List<IndexBean>?){
    (recyclerView.adapter as? WeatherIndexListAdapter)?.setList(list)
}

@BindingAdapter("videoList")
fun videoData(recyclerView: RecyclerView,list: List<VideoType>?){
    (recyclerView.adapter as? VideoAdapter)?.setList(list)
}

@BindingAdapter("moreVideoList")
fun loadMoreVideoData(recyclerView: RecyclerView,viewModel: VideoViewModel){
    (recyclerView.adapter as? VideoAdapter)?.loadMoreModule?.setOnLoadMoreListener {
        viewModel.loadMoreList()
    }
}

@BindingAdapter("hotWordList")
fun hotWordList(recyclerView: RecyclerView,list:List<String>?){
    (recyclerView.adapter as? HotKeywordsAdapter)?.setList(list)
}

@BindingAdapter("searchHistoryList")
fun searchHistoryList(recyclerView: RecyclerView,list:List<SearchHistoryBean>?){
    (recyclerView.adapter as? SearchHistoryAdapter)?.setList(list)
}

@BindingAdapter("searchResultList")
fun searchResultList(recyclerView: RecyclerView,list: List<Item>?){
    (recyclerView.adapter as? VideoSearchResultAdapter)?.setList(list)
}

@BindingAdapter("videoDetailList")
fun videoDetailList(recyclerView: RecyclerView,list: List<Item>?){
    (recyclerView.adapter as? VideoDetailAdapter)?.setList(list)
}

@BindingAdapter("watchRecordList")
fun watchRecordList(recyclerView: RecyclerView,list:List<Data>?){
    (recyclerView.adapter as? WatchHistoryAdapter)?.setList(list)
}

@BindingAdapter("cityList")
fun cityList(recyclerView: RecyclerView,list:List<CityManageBean>?){
    (recyclerView.adapter as? CityManageAdapter)?.setList(list)
}

@BindingAdapter("searchCityList")
fun searchCityList(recyclerView: RecyclerView,list:List<ChinesePlaceBean>?){
    (recyclerView.adapter as? CityChooseAdapter)?.setList(list)
}