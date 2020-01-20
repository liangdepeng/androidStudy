package ldp.example.com.android_demo.studydemo.views

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import ldp.example.com.android_demo.R

/**
 *  @author  mini
 *  @date 2019/10/24
 */
class MyBroadcastReceiver2 : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent?.action.equals("ldp.example.com.android_demo.studydemo.broadcastReceiver2")) {
//            Toast.makeText(context,"222",Toast.LENGTH_SHORT).showVerticalAutomatic()
            val toast = Toast(context)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.view = LayoutInflater.from(context).inflate(R.layout.broadcast_layout, null, false)
            toast.show()
        }
    }
}