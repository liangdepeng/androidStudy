package ldp.example.com.android_demo.studydemo.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.ldp.base_lib.base.MyBaseActivity
import com.example.ldp.base_lib.utils.AppUtils
import com.example.ldp.base_lib.utils.LogUtils
import kotlinx.android.synthetic.main.activity_web_view.*
import ldp.example.com.android_demo.MyApplication
import ldp.example.com.android_demo.R


class WebViewActivity : MyBaseActivity() {

    lateinit var webView: WebView
    private var click_back_time: Long = 0

    override fun getLayoutResId(): Int {
        return R.layout.activity_web_view
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView() {
        webView = WebView(MyApplication.getAppContent())
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        frameLayoutShowWeb.addView(webView, layoutParams)

        setWebViewSetting("")
        web_view_btn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                setWebViewSetting(AppUtils.stringRemoveSpace(web_view_et.text))
            }
        })

        getUrl.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                showShortToast(webView.url)
                web_view_et.setText(webView.url)
            }
        })
    }

    private fun setWebViewSetting(url: String?) {
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        //缩放操作
        webSettings.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
        webSettings.builtInZoomControls = true //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.displayZoomControls = false //隐藏原生的缩放控件

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

        if (url != "") {
            webView.loadUrl(url)
        } else {
            webView.loadUrl("https://www.baidu.com/")
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                view?.loadUrl(request.toString())
                LogUtils.d("webViewClient", "  --shouldOverrideUrlLoading--  ")
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                LogUtils.d("webViewClient", "  --onPageStarted--  ")
                showProgressDialog()
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                LogUtils.d("webViewClient", "  --onPageFinished--  ")
                hideProgressDialog()
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {

                handler?.proceed() //忽略错误加载
                //handler?.cancel() //表示挂起连接，为默认方式 (super.onReceivedSslError(view, handler, error))
                // handler?.handleMessage(null) //可做其他处理
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                if (request != null && request.isForMainFrame) {
                    webView.loadUrl("https://www.baidu.com/")
                }
                Toast.makeText(this@WebViewActivity, "加载网页出错", Toast.LENGTH_SHORT).show()
            }

            override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                super.onReceivedError(view, errorCode, description, failingUrl)
            }

            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
                super.onReceivedHttpError(view, request, errorResponse)
            }
        }

        webView.webChromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView?, title: String?) {
                LogUtils.d("webView", title)
            }

            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                LogUtils.d("webView", newProgress.toString() + "%")
            }

        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack()
            } else {
                if (System.currentTimeMillis() - click_back_time < 2000) {
                    finish()
                } else {
                    showShortToast("2秒再按一次退出程序")
                    click_back_time = System.currentTimeMillis()
                }
            }

            return true
        }
        return super.onKeyDown(keyCode, event)
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

    override fun startRequestInfo() {

    }

    override fun initPresenter() {

    }

    override fun initData() {

    }

}
