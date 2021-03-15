package com.news.simple_news.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.VideoView

class CustomVideoView(context: Context,attrs:AttributeSet?,defStyleAttr:Int) :VideoView(context, attrs, defStyleAttr){
    constructor(context: Context,attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width= View.getDefaultSize(0,widthMeasureSpec)
        val height= View.getDefaultSize(0,heightMeasureSpec)
        setMeasuredDimension(width,height)
    }
}