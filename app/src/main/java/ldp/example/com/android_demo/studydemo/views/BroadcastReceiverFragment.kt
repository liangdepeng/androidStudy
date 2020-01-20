package ldp.example.com.android_demo.studydemo.views


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_broadcast_receiver.*

import ldp.example.com.android_demo.R

/**
 * A simple [Fragment] subclass.
 */
class BroadcastReceiverFragment : Fragment() {

    private lateinit var myBroadcastReceiver: MyBroadcastReceiver

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_broadcast_receiver, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        intiView()
        initData()
    }

    private fun initData() {
        myBroadcastReceiver = MyBroadcastReceiver()
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        intentFilter.addAction("ldp.example.com.android_demo.studydemo.broadcastReceiver")
        activity?.registerReceiver(myBroadcastReceiver, intentFilter)
    }

    private fun intiView() {
        btn.setOnClickListener {
            activity?.sendBroadcast(Intent("ldp.example.com.android_demo.studydemo.broadcastReceiver"))
        }
    }

    inner class MyBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when {
                intent?.action == "ldp.example.com.android_demo.studydemo.broadcastReceiver" -> {
                    val toast = Toast(context)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    toast.view = LayoutInflater.from(context).inflate(R.layout.broadcast_layout, null, false)
//                    activity?.packageName
                    toast.show()
                }
                intent?.action == "android.net.conn.CONNECTIVITY_CHANGE" ->
                    Toast.makeText(context, "网络状态发生改变~", Toast.LENGTH_SHORT).show()
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        activity?.unregisterReceiver(myBroadcastReceiver)
    }
}
