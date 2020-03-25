package ldp.example.com.android_demo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.ldp.base_lib.utils.LogUtils;

public class TestBindService extends Service {

    private Binder binder = new LocalBinder();

    public class LocalBinder extends Binder {
        TestBindService getService() {
            return TestBindService.this;
        }
    }

    @Override
    public void onCreate() {
        LogUtils.e("onCreate", "onCreate");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        LogUtils.e("onDestroy", "onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.e("onStartCommand","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("onBind","onBind");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtils.e("onUnbind","onUnbind");
        return super.onUnbind(intent);
    }

    public String getBindInfo() {
        return "I AM BINDINFO";
    }
}
