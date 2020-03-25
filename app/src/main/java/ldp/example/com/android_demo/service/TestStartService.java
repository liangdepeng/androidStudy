package ldp.example.com.android_demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;

import ldp.example.com.android_demo.MyApplication;

/**
 * @author mini
 */
public class TestStartService extends Service {

    /**
     * 首次创建Service调用执行一次，如果服务已经运行，不会调用此方法
     */
    @Override
    public void onCreate() {
        LogUtils.e("onCreateService", AppUtils.getThreadInfo());
        super.onCreate();
    }

    /**
     * 每次 通过startService 方法调用服务 都会调用此方法
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand", "onStartCommand");
        Toast.makeText(MyApplication.getAppContent(),"onStartCommand",Toast.LENGTH_LONG).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        LogUtils.e("onServiceDestroy","onDestroy");
        super.onDestroy();
    }
}
