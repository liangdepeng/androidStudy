package ldp.example.com.android_demo.studydemo.yibuxiaoxi;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.ldp.base_lib.bean.WeatherBean;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author ldp
 */
public class MyIntentService extends IntentService {

    public static IntentServiceMvpActivity.UpdateInfoListener listener;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param Name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService() {
        super("threadName1112");
    }

    /**
     * 执行异步任务
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        WeatherBean weatherBean = null;
        StringBuilder result = new StringBuilder();
        try {
            if (intent == null) return;
            String httpUrl = intent.getStringExtra("url");
            if (httpUrl == null) return;
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            //StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
            weatherBean = new Gson().fromJson(result.toString(), WeatherBean.class);
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (weatherBean != null && listener != null) {
            listener.update(weatherBean);
        }
    }

    public static void setListener(IntentServiceMvpActivity.UpdateInfoListener listener1) {
        listener = listener1;
    }
}
