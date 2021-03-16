package com.news.simple_news.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.simple_news.model.repository.Repository
import com.news.simple_news.util.loge
import com.news.simple_news.util.toast
import com.google.gson.JsonParseException
import com.news.simple_news.R
import com.news.simple_news.model.bean.CityManageBean
import com.news.simple_news.model.room.RoomHelper
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.json.JSONException
import retrofit2.HttpException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.ParseException

typealias Block<T> = suspend()->T
typealias Error = suspend (e:Exception)->Unit

open class BaseViewModel :ViewModel(){

    var mCurrentCity=MutableLiveData<Int>()
    var mChooseCity=MutableLiveData<List<CityManageBean>>()

    fun changeCurrentCity(position:Int){
        mCurrentCity.value=position
    }

    fun queryChooseCity(){
        launch({RoomHelper.getCityList()})
    }



    protected val repository by lazy { Repository() }

    /**
     * 创建并执行协程 同步
     */
    protected fun launch(block: Block<Unit>, error: Error?=null): Job {
        return viewModelScope.launch {
            try {
                block.invoke()
            }catch (e:Exception){
                onError(e)
                error?.invoke(e)
            }
        }
    }

    /**
     * 创建并执行协程 异步
     */
    protected fun <T> async(block: Block<T>): Deferred<T> = viewModelScope.async { block.invoke() }

    private fun onError(e:Exception){
        when(e){
            is HttpException -> toast(R.string.network_not_available)
            is ConnectException,is UnknownHostException -> toast(R.string.network_not_available)
            is SocketTimeoutException,is InterruptedIOException -> toast(R.string.network_request_timeout)
            is JsonParseException,is JSONException,is ParseException -> toast(R.string.api_data_parse_error)
            else->e.message?.let { loge(it) }
        }
    }
}