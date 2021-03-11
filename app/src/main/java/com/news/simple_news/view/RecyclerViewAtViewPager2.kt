package com.news.simple_news.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class RecyclerViewAtViewPager2(@NonNull context: Context, @Nullable attrs: AttributeSet?, defStyleAttr: Int)  :
    RecyclerView(context, attrs, defStyleAttr) {
    private var startX = 0
    private var startY = 0

    constructor(@NonNull context: Context):this(context,null)


    constructor(@NonNull context: Context, attrs: AttributeSet?): this(context, attrs,0)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when(ev.action){
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x.toInt()
                startY = ev.y.toInt()
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                val endX = ev.x.toInt()
                val endY = ev.y.toInt()
                val disX = abs(endX - startX)
                val dixY = abs(endY - startY)
                if (disX*0.5 > dixY) {
                    parent.requestDisallowInterceptTouchEvent(canScrollHorizontally(startX - endX))
                } else {
                    parent.requestDisallowInterceptTouchEvent(canScrollVertically(startY - endY))
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> parent.requestDisallowInterceptTouchEvent(false)
        }
        return super.dispatchTouchEvent(ev)
    }
}