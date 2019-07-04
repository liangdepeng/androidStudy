package ldp.example.com.android_demo.studydemo.webview

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Toast
import com.example.ldp.base_lib.utils.LogUtils
import kotlinx.android.synthetic.main.activity_welcome.*
import ldp.example.com.android_demo.R
import java.lang.ref.WeakReference

class HandlerWelcomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var weakReference :WeakReference<Activity>
    private val TAG = 100
    private var loadingTime = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        initData()
    }

    private fun initData() {
        image.setImageResource(R.mipmap.school_)
        pause_start.setOnClickListener(this)
        handler.sendEmptyMessage(TAG)

        val message1 = Message.obtain()
        message1.what = 1
        message1.obj = " handler.sendMessage(message1) + 123喵喵喵 "
        handler.sendMessage(message1)

        val message = Message.obtain(handler, Runnable {
            LogUtils.e("handler", "handler.dispatchMessage(message)")
        })
        message.what = 2
        handler.dispatchMessage(message)

        val message2 = Message.obtain(handler, null)
        message2.what = 2
        handler.dispatchMessage(message2)


        Thread(Runnable {
            Thread.sleep(3_000)
            handler.post(Runnable {
                Toast.makeText(this, "handler post", Toast.LENGTH_SHORT).show()
            })
        }).start()
    }

    private var handler: Handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            when (msg?.what) {
                TAG -> {
                    LogUtils.e("handler", "$loadingTime handler.sendEmptyMessage(TAG) \n")
                    loading_time.text = String.format(getString(R.string.loading_time), loadingTime.toString())
                    loadingTime--
                    msg.target.removeMessages(TAG)
                    if (loadingTime < 0) {
                        startActivity(Intent(this@HandlerWelcomeActivity, WebViewActivity::class.java))
                        finish()
                    } else {
                        msg.target.sendEmptyMessageDelayed(TAG, 1_000)
                    }
                }
                1 -> {
                    val obj = msg.obj
                    LogUtils.e("handler", obj.toString())
                }
                2 -> {
                    LogUtils.e("handler", "callback is null -> handler.dispatchMessage(message)")
                }
            }
        }
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this@HandlerWelcomeActivity, WebViewActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

}
