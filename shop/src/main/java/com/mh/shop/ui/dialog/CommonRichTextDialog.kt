package com.mh.shop.ui.dialog

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mh.shop.ext.isHttpUrl
import com.mh.shop.http.MainViewModel
import com.mh0505.shop.databinding.DialgoCommonRichtextBinding
import com.mh55.easy.ext.singleClick
import com.mh55.easy.ui.dialog.BaseDialog

class CommonRichTextDialog(val mTitle: String, val mContent: String) :
    BaseDialog<DialgoCommonRichtextBinding, MainViewModel>() {
    override fun main(savedInstanceState: Bundle?) {
        super.main(savedInstanceState)
        with(mDialogBinding) {
            close.singleClick { dismiss() }
            mTvTitle.text = mTitle
            initWebView()
            if (mContent.isHttpUrl()) {
                loadUrl()
            } else {
                loadRich()
            }
        }
    }

    private fun loadUrl() {
        with(mDialogBinding) {
            mWebView.loadUrl(mContent.trim())
        }
    }

    private fun loadRich() {
        with(mDialogBinding) {
            mWebView.loadDataWithBaseURL("", getHtmlData(mContent), "text/html", "utf-8", null)
        }
    }

    private fun initWebView() {
        with(mDialogBinding) {
            mWebView.apply {
                webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        view?.loadUrl(request?.url.toString())
                        return true
                    }

                }
                settings.apply {
                    //如果访问的页面中要与Javascript交互，则webView必须设置支持Javascript
                    // 若加载的 html 里有JS 在执行动画等操作，会造成资源浪费（CPU、电量）
                    // 在 onStop 和 onResume 里分别把 setJavaScriptEnabled() 给设置成 false 和 true 即可
                    javaScriptEnabled = true
                    // 设置允许JS弹窗
                    javaScriptCanOpenWindowsAutomatically = true
                    //页面自适应 两者合用
                    useWideViewPort = true
                    loadWithOverviewMode = true
                    //缩放操作
                    setSupportZoom(true)//支持缩放，默认为true。是下面那个的前提。
                    builtInZoomControls = true  //设置内置的缩放控件。若为false，则该WebView不可缩放
                    displayZoomControls = true //隐藏原生的缩放控件
                    //其他细节操作
                    cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webView中缓存
                    allowFileAccess = true //设置可以访问文件
                    javaScriptCanOpenWindowsAutomatically = true  //支持通过JS打开新窗口
                    loadsImagesAutomatically = true //支持自动加载图片
                    defaultTextEncodingName = "utf-8"//设置编码格式
                    // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    }
                }
                clearCache(true)
                setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY)//取消滚动条白边效果
            }
        }
    }

    override fun dismiss() {
        mDialogBinding.mWebView.clearHistory()
        super.dismiss()
    }

    override fun setGravity(): Int {
        return Gravity.BOTTOM
    }

    override fun onDestroy() {
        if (mDialogBinding.mWebView != null) {
            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，
            //需要先onDetachedFromWindow()，再destory()
            val parent = mDialogBinding.mWebView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(mDialogBinding.mWebView)
            }
            with(mDialogBinding.mWebView) {
                clearCache(true)
                stopLoading()
                // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
                settings.javaScriptEnabled = false
                clearHistory()
                removeAllViews()
                destroy()
            }

        }
        super.onDestroy()

    }

    /**
     * 富文本适配
     */
    private fun getHtmlData(bodyHTML: String): String {
        val head = ("<head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
                + "<style>img{max-width: 100%; width:auto; height:auto;}</style>"
                + "</head>")
        return "<html>$head<body>$bodyHTML</body></html>"
    }

}