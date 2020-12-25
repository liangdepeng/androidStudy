package ldp.example.com.android_demo.main;

import ldp.example.com.android_demo.livedata.FragmentTestActivity;
import ldp.example.com.android_demo.livedata.LiveDataTestActivity;
import ldp.example.com.android_demo.service.TestServiceActivity;
import ldp.example.com.android_demo.studydemo.broadcast.BroadcastActivity;
import ldp.example.com.android_demo.studydemo.callphone.CallphonePermissionActivity;
import ldp.example.com.android_demo.studydemo.contentprovider.ContentProviderPermissionActivity;
import ldp.example.com.android_demo.studydemo.dialog.DialogsActivity;
import ldp.example.com.android_demo.studydemo.http.HttpActivity;
import ldp.example.com.android_demo.studydemo.json.JsonPermissionActivity;
import ldp.example.com.android_demo.studydemo.kotlin.KotlinVideoPlayerActivity;
import ldp.example.com.android_demo.studydemo.notification.NotificationActivity;
import ldp.example.com.android_demo.studydemo.pic.ImageCaptureMvpActivity;
import ldp.example.com.android_demo.studydemo.qqlogin.QQLogin_Activity;
import ldp.example.com.android_demo.studydemo.sendmessage.SendMessagePermissionActivity;
import ldp.example.com.android_demo.studydemo.sqllitedemo.SqlActivity;
import ldp.example.com.android_demo.studydemo.testcrash.TestCrashActivity;
import ldp.example.com.android_demo.studydemo.webview.WebViewMvpActivity;
import ldp.example.com.android_demo.studydemo.webview.androidH5Activity;
import ldp.example.com.android_demo.studydemo.wrisesdfile.SharedPreferenceActivity;
import ldp.example.com.android_demo.studydemo.wrisesdfile.WriteActivity;
import ldp.example.com.android_demo.studydemo.wrisesdfile.WriteSDfilePermissionActivity;
import ldp.example.com.android_demo.studydemo.xml_.XmlPermissionActivity;
import ldp.example.com.android_demo.studydemo.yibuxiaoxi.AsyncTaskActivity;
import ldp.example.com.android_demo.studydemo.yibuxiaoxi.HandlerThreadPermissionActivity;
import ldp.example.com.android_demo.studydemo.yibuxiaoxi.HandlerWelcomeActivity;
import ldp.example.com.android_demo.studydemo.yibuxiaoxi.IntentServiceMvpActivity;
import ldp.example.com.android_demo.studydemo.zidingyikj.circleActivity;
import ldp.example.com.android_demo.weatherdemo.WeatherSplashActivity;

/**
 * created by Da Peng at 2019/7/3
 */
public enum ClassEnum {

    //XML 解析和生成
    XML_("xml解析和生成", XmlPermissionActivity.class, false),

    //读写sd卡
    IO_SDCARD("读写sd卡", WriteSDfilePermissionActivity.class, false),

    // 读写文件（IO）
    IO("读写文件", WriteActivity.class, false),

    //SharedPreference存储
    SHARED_PREFERENCE("SharedPreference存储", SharedPreferenceActivity.class, false),

    // webView
    WEB_VIEW("webView", WebViewMvpActivity.class, false),

    // handler
    HANDLER("handler通信", HandlerWelcomeActivity.class, false),

    //Service
    SERVICE("service", TestServiceActivity.class, false),

    // Broadcast 广播接收器
    BROADCASTRECEIVER("broadcastReceiver", BroadcastActivity.class, false),

    // asyncTask 异步任务
    ASYNC_TASK("asyncTask异步任务", AsyncTaskActivity.class, false),

    // handlerThread 工作线程+handler
    HANDLER_THREAD("handlerThread", HandlerThreadPermissionActivity.class, false),

    //IntentService
    INTENT_SERVICE("intentService", IntentServiceMvpActivity.class, false),

    // SQLite数据库
    SQ_LITE("SQLite", SqlActivity.class, false),

    // 发短信 SMS
    SMS("发短信", SendMessagePermissionActivity.class, false),

    // QQ_login
    QQ_LOGIN("QQ_LOGIN", QQLogin_Activity.class, false),

    // 相册
    PHOTO_ALBUM("相册", ImageCaptureMvpActivity.class, false),

    // 通知栏
    NOTIFICATION("通知栏演示", NotificationActivity.class, false),

    // 天气预报信息（数据库Demo）
    WEATHER_DEMO("天气预报信息（数据库Demo）", WeatherSplashActivity.class, false),

    // 简单视频播放器
    VIDEO_PLAYER("videoPlayer(模拟播放器)", KotlinVideoPlayerActivity.class, false),

    // liveData+viewModel(Activity)
    LIVE_DATA_ONE("liveData+viewModel(Activity)", LiveDataTestActivity.class, false),

    //liveData+viewModel(fragment共用ViewModel)
    LIVE_DATA_TWO("liveData+viewModel(fragment共用ViewModel)", FragmentTestActivity.class, false),

    // json数据解析
    JSON_DATA("json数据解析", JsonPermissionActivity.class, false),

    // 安卓原生网络请求
    HTTP_REQUEST("原生http网络请求", HttpActivity.class, false),

    // 内容提供者
    CONTENT_PROVIDER("contentProvider", ContentProviderPermissionActivity.class, false),

    // 拨打电话
    CALL_PHONE("拨打电话", CallphonePermissionActivity.class, false),

    // 安卓原生弹窗
    ANDROID_DIALOG("安卓原生弹窗", DialogsActivity.class, false),

    ANDROID_H5("安卓基本h5交互", androidH5Activity.class, false),

    CIRCLEACTIVITY("test", circleActivity.class, false),

    TEST_CRASH_INFO("crash_Test",TestCrashActivity.class,false);

    private String info;
    private Class<?> clazz;
    private boolean defaultStart;

    private ClassEnum(String info, Class<?> clazz, boolean defaultStart) {
        this.info = info;
        this.clazz = clazz;
        this.defaultStart = defaultStart;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public boolean isDefaultStart() {
        return defaultStart;
    }

    public void setDefaultStart(boolean defaultStart) {
        this.defaultStart = defaultStart;
    }
}
