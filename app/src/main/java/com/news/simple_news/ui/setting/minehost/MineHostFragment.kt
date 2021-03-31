package com.news.simple_news.ui.setting.minehost

import android.os.Bundle
import android.webkit.WebView
import android.widget.LinearLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import com.news.simple_news.R
import com.news.simple_news.base.BaseFragment
import com.news.simple_news.databinding.FragmentMinehostBinding
import java.lang.StringBuilder

class MineHostFragment : BaseFragment<FragmentMinehostBinding>() {
    companion object {
        fun newInstance(): MineHostFragment {
            return MineHostFragment()
        }
    }

    private val projectHost = "https://github.com/Aronchenf/SimpleNews"

    private lateinit var mAgentWeb: AgentWeb
    override fun initLayout(): Int = R.layout.fragment_minehost

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mBinding.toolbar.setNavigationOnClickListener {
            popBack()
        }
        initWeb()
    }

    private fun initWeb() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(mBinding.hostContainer, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator(-1, 3)
            .interceptUnkownUrl()
            .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
            .setWebChromeClient(chromeClient)
            .setWebViewClient(webViewClient)
            .createAgentWeb()
            .ready()
            .go(projectHost)
    }

    private val chromeClient = object : WebChromeClient() {
        override fun onReceivedTitle(view: WebView?, title: String?) {
            super.onReceivedTitle(view, title)
            setTitle(title!!)
        }
    }

    private val webViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            view?.loadUrl(customJs())
            super.onPageFinished(view, url)
        }
    }

    private fun setTitle(title: String) {
        mBinding.toolbar.title = title
    }

    private fun customJs(): String {
        val js = StringBuilder()
        js.append("javascript:(function(){")
        js.append("var headers=document.getElementsByClassName(\"position-relative js-header-wrapper \");")
        js.append("if(headers){headers[0].parentNode.removeChild(headers[0]);}")
        js.append("})()")
        return js.toString()
    }

}