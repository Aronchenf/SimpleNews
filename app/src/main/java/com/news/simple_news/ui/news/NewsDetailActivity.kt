package com.news.simple_news.ui.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import androidx.core.content.ContextCompat
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.news.simple_news.R
import com.news.simple_news.base.BaseActivity
import com.news.simple_news.databinding.ActivityNewsdetailBinding

import java.lang.StringBuilder

class NewsDetailActivity : BaseActivity<ActivityNewsdetailBinding>() {

    companion object{
        const val URL="URL"
    }

    override fun initLayout(): Int = R.layout.activity_newsdetail

    private var webLink:String?=null
    private lateinit var agentWeb: AgentWeb

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.toolbar.setNavigationIcon(R.drawable.arrow_left_black)
        mBinding.toolbar.setNavigationOnClickListener { this.onBackPressed() }
        webLink=intent.getStringExtra(URL)
        initWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView() {
        agentWeb=AgentWeb.with(this)
            .setAgentWebParent(mBinding.webContainer,ViewGroup.LayoutParams(-1,-1))
            .useDefaultIndicator(ContextCompat.getColor(this,R.color.dim_gray),2)
            .interceptUnkownUrl()
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(object : WebChromeClient(){
                override fun onReceivedTitle(view: WebView?, title: String?) {
                    setToolTitle(title)
                    super.onReceivedTitle(view, title)
                }
            }).setWebViewClient(object :WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    view?.loadUrl(customJs())
                    super.onPageFinished(view, url)
                }
            })
            .createAgentWeb()
            .ready()
            .get()

        agentWeb.webCreator?.webView?.run {
            overScrollMode=WebView.OVER_SCROLL_NEVER
            settings.run {
                javaScriptCanOpenWindowsAutomatically=false
                loadsImagesAutomatically=true
                useWideViewPort=true
                loadWithOverviewMode=true
            }
        }
        agentWeb.urlLoader?.loadUrl(webLink)
    }

    private fun setToolTitle(title:String?){
        mBinding.titleToolbar.text = title
    }

    //编写js代码
    private fun customJs():String{
        val js= StringBuilder()
        js.append("javascript:(function(){")
        js.append("var tops=document.getElementsByClassName('sw_c0 fix_top');")
        js.append("if(tops){tops[0].parentNode.removeChild(tops[0]);}")
        js.append("var titles=document.getElementsByClassName('page_main fl_padding');")
        js.append("if(titles) titles[0].style.padding=0;")
        js.append("var headers=document.getElementsByClassName('hd_s1');")
        js.append("if(headers) headers[0].parentNode.removeChild(headers[0]);")
        js.append("var openApp=document.getElementsByClassName('look_more_a');")
        js.append("openApp[0].parentNode.removeChild(openApp[0]);")
        js.append("var icons=document.getElementsByClassName('weibo_info look_info');")
        js.append("icons[0].parentNode.removeChild(icons[0]);")
        js.append("var bottoms=document.getElementsByClassName('fl_words rf j_cmnt_bottom');")
        js.append("if(bottoms){bottoms[0].parentNode.removeChild(bottoms[0]);}")
        js.append("var openInApp=document.getElementsByClassName('callApp_fl_btn j_callST');")
        js.append("if(openInApp){openInApp[0].parentNode.removeChild(openInApp[0]);}")
        js.append("var s_card=document.getElementById('j_card_miniVideo');")
        js.append("if(s_card){s_card.parentNode.removeChild(s_card);}")
        js.append("var recommend_read=document.getElementById('j_relevent_reading');")
        js.append("if(recommend_read){recommend_read.parentNode.removeChild(recommend_read);}")
        js.append("var relevant_news=document.getElementsByClassName('s_card j_article_relevent_news z_c2');")
        js.append("if(relevant_news){relevant_news[0].parentNode.removeChild(relevant_news[0]);}")
        js.append("var videoAd=document.getElementsByClassName('s_card j_video_with_ad z_c2');")
        js.append("if(videoAd){videoAd[0].parentNode.removeChild(videoAd[0]);}")
        js.append("var relevant_video=document.getElementsByClassName('s_card j_relevent_video');")
        js.append("if(relevant_video){relevant_video[0].parentNode.removeChild(relevant_video[0]);}")
        js.append("var recommend_read=document.getElementsByClassName('s_card j_article_relevent j_wbrecommend_wrap');")
        js.append("if(recommend_read){recommend_read[0].parentNode.removeChild(recommend_read[0]);}")
        js.append("})()")
        return js.toString()
    }

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return if (agentWeb.handleKeyEvent(keyCode,event)){
//            return true
//        }else{
//            super.onKeyDown(keyCode, event)
//        }
//    }

    override fun onPause() {
        agentWeb.webLifeCycle.onPause()
        super.onPause()
    }

    override fun onResume() {
        agentWeb.webLifeCycle.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb.webLifeCycle.onDestroy()
        super.onDestroy()
    }

}