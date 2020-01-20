package ldp.example.com.android_demo.studydemo.webview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.example.ldp.base_lib.utils.AppUtils
import com.example.ldp.base_lib.utils.LogUtils
import kotlinx.android.synthetic.main.activity_welcome.*
import ldp.example.com.android_demo.MyApplication
import ldp.example.com.android_demo.R

class HandlerWelcomeActivity : AppCompatActivity(), View.OnClickListener {

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
                    thread2()
                    thread3()
                }
            }
        }
    }

    private var handler2: MyHandler? = null

    fun thread2() {
        Thread(Runnable {
            LogUtils.e("thread2", AppUtils.getThreadInfo())
            Looper.prepare()
            handler2 = MyHandler
            Looper.loop()
        }).start()

    }

    object MyHandler : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                0 -> {
                    Toast.makeText(MyApplication.getAppContent(), "3_000", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(MyApplication.getAppContent(), "121212", Toast.LENGTH_SHORT).show()
                    msg?.target?.sendEmptyMessageDelayed(0, 3_000)
                }
            }

        }
    }

    fun thread3() {
        val message = Message.obtain()
        message.what = 1
        Thread(Runnable {
            LogUtils.e("thread3", AppUtils.getThreadInfo())
            handler2?.sendMessage(message)
        }).start()
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this@HandlerWelcomeActivity, WebViewActivity::class.java))
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
        handler2?.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

}
