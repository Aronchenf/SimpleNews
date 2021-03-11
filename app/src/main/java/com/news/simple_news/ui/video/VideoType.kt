package com.news.simple_news.ui.video

import androidx.annotation.IntDef
import com.news.simple_news.model.bean.Item

class VideoType(val type:Int, val item: Item? = null, val items: List<Item> = mutableListOf()) {
    @IntDef(value = [Type.BANNER, Type.HEADER, Type.CONTENT])
    @Target(AnnotationTarget.VALUE_PARAMETER)
    @Retention(AnnotationRetention.SOURCE)

    annotation class Type{
        companion object {
            const val BANNER = 1
            const val HEADER = 2
            const val CONTENT = 3
        }
    }



}