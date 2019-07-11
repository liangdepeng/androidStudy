package ldp.example.com.android_demo.studydemo.webview

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.androidh5_layout.*
import ldp.example.com.android_demo.MyApplication
import ldp.example.com.android_demo.R

/**
 *  created by Da Peng at 2019/7/10
 */

class androidH5Activity : AppCompatActivity() {

    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.androidh5_layout)
        initView()
    }


    private fun initView() {
        webView = WebView(MyApplication.getAppContent())
        val webSettings = webView.settings

        setWebViewSettings(webSettings)
        webView.addJavascriptInterface(JsInterFace(), "control")
        webView.loadUrl("file:///android_asset/show.html")

        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        webViewH5Layout.addView(webView, layoutParams)
        androidToH5.setOnClickListener { webView.loadUrl("javascript:test()") }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setWebViewSettings(webSettings: WebSettings) {
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        //webSettings.displayZoomControls = false //隐藏原生的缩放控件

        //其他细节操作
        webSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK //关闭webview中缓存
        webSettings.allowFileAccess = true //设置可以访问文件
        webSettings.javaScriptCanOpenWindowsAutomatically = true //支持通过JS打开新窗口
        webSettings.loadsImagesAutomatically = true //支持自动加载图片
        webSettings.defaultTextEncodingName = "utf-8"//设置编码格式

        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        //缓存模式如下：
        // LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        // LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        // LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        // LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
    }

    inner class JsInterFace {

        @JavascriptInterface
        fun showTips(string: String) {
            Toast.makeText(this@androidH5Activity, string, Toast.LENGTH_SHORT).show()
        }

        @JavascriptInterface
        fun chooseTime(){
            TimePickerDialog(this@androidH5Activity,object : TimePickerDialog.OnTimeSetListener{
                override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                    showTips("$hourOfDay  $minute")
//                    webView.loadUrl("javascript:test2("+ "$hourOfDay  $minute"+")")
                }
            },0,0,true).show()
        }
    }

    override fun onDestroy() {
        destroyWebView()
        super.onDestroy()
    }

    private fun destroyWebView() {
        webView.settings.javaScriptEnabled = false
        webView.stopLoading()
        webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        webView.clearHistory()
        (webView.parent as ViewGroup).removeAllViews()
        webView.destroy()
    }
}