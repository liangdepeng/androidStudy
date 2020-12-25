package ldp.example.com.android_demo.studydemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import ldp.example.com.android_demo.MyApplication;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Toast.makeText(MyApplication.getAppContent(),action,Toast.LENGTH_SHORT).show();
        }
    }
}
