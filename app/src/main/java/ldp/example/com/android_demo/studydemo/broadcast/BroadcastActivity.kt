package ldp.example.com.android_demo.studydemo.broadcast

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import kotlinx.android.synthetic.main.activity_broadcast.*
import ldp.example.com.android_demo.R

class BroadcastActivity : AppCompatActivity() {

    private val myReceiver = MyReceiver()
    private val myReceiver2 = MyReceiver()
    private lateinit var localBroadcastManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast)

        // 一般的广播  还有 有序广播，sticky广播 保留最后一条广播 持久保留 只要有新的接收器 都可以收到
        // https://blog.csdn.net/qq_41684370/article/details/80675305?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
        val intentFilter = IntentFilter()
        intentFilter.addAction("caseOne")
        intentFilter.addAction("caseTwo")
        registerReceiver(myReceiver, intentFilter)

        //本地广播 只在app内传播 更加安全高效 handler实现
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        localBroadcastManager.registerReceiver(myReceiver2, intentFilter)

        initView()
    }

    private fun initView() {
        sendBroadcastBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                sendBroadcast(Intent("caseOne"))
            }
        })

        sendLocalBroadcastBtn.setOnClickListener {
            localBroadcastManager.sendBroadcast(Intent("caseTwo"))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(myReceiver)
        localBroadcastManager?.unregisterReceiver(myReceiver2)
    }

}