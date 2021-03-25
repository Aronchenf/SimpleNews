package com.news.simple_news.model.repository

import com.news.simple_news.model.room.RoomHelper

class AppRepository {
    suspend fun queryAllChooseArea()=RoomHelper.getCityList()
}