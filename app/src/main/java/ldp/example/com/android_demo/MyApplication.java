package ldp.example.com.android_demo;

import com.example.ldp.base_lib.base.BaseApplication;
import com.example.ldp.base_lib.utils.LogUtils;

import org.litepal.LitePal;
import org.xutils.x;

/**
 * created by ldp at 2018/8/3
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        x.Ext.init(this);
        //输出debug日志，开启会影响性能
        x.Ext.setDebug(false);
        LitePal.initialize(this);
        LogUtils.setDebug(true);
        // 全局异常拦截
       // ExceptionCrashHandler.getInstance().initInterceptCrash(getApplicationContext());
    }
}
