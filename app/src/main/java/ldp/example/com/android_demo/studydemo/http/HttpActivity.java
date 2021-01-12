package ldp.example.com.android_demo.studydemo.http;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.utils.AsyncUtils;

public class HttpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtn_http;
    private TextView mTxt_http;
    private EditText mEt_http;
    private EditText mAddress_et;
    private Button mWeather_btn;
    private boolean isRequsetWeather = false;
    private Executor threadPool;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http);
        mBtn_http = (Button) findViewById(R.id.btn_http_btn);
        mTxt_http = (TextView) findViewById(R.id.txt_http);
        mEt_http = (EditText) findViewById(R.id.et_http);
        mAddress_et = (EditText) findViewById(R.id.address_et);
        mWeather_btn = (Button) findViewById(R.id.request_weather_btn);
        mWeather_btn.setOnClickListener(this);
        //mEt_http.setText("杭州");
        httpTest2();


        // EditText 弹出键盘导致界面上移错乱，解决方法，最外层布局设置为 ScrollView
        // 或者 功能清单文件 activity设置 android:windowSoftInputMode=“adjustResize|stateHidden”
        // EditText 默认导致软键盘弹出问题  直接父布局设置以下属性
        //    android:focusable="true"
        //    android:focusableInTouchMode="true"
        //
        //   终极决方案  ↓↓↓↓↓
        //  https://blog.csdn.net/u011622479/article/details/51161717
    }

    /**
     * 网络请求带中文  低版本 需要 encode
     */
    private void httpTest2() {
        iAsyncTask = new AsyncUtils.IAsyncTask() {
            @Override
            public Object onAsyncTask() {
                HttpURLConnection connection = null;
                URL url = null;
                try {
                    Thread.sleep(5000);
                    if (isRequsetWeather) {
                        isRequsetWeather = false;
                        url = new URL("https://free-api.heweather.com/s6/weather?location=" + URLEncoder.encode(AppUtils.stringRemoveSpace(mAddress_et.getText()), "UTF-8") + "&key=e213b21d1a25491ba625ce3d3d5dfc2d");
                    } else {
                        if (!TextUtils.equals(mEt_http.getText().toString().trim(), "")) {
                            url = new URL("https://free-api.heweather.com/s6/weather?location=" + URLEncoder.encode(mEt_http.getText().toString().trim(), "UTF-8") + "&key=e213b21d1a25491ba625ce3d3d5dfc2d");
                        } else {
                            url = new URL("https://free-api.heweather.com/s6/weather?location=" + URLEncoder.encode("杭州", "UTF-8") + "&key=e213b21d1a25491ba625ce3d3d5dfc2d");
                        }
                    }
                    LogUtils.d("httpActivity", url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    LogUtils.d("httpActivity", result.toString());
                    return result.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            public void onTaskComplete(Object o) {
                showResult(((String) o));
            }
        };
        AsyncUtils.performTask(iAsyncTask, "test");
    }

    private AsyncUtils.IAsyncTask iAsyncTask;

    private void httpTest() {
        // 使用线程池，避免显式创建线程
        if (threadPool == null) {
            threadPool = Executors.newCachedThreadPool();
        }
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                URL url = null;
                try {
                    if (isRequsetWeather) {
                        isRequsetWeather = false;
                        url = new URL("https://free-api.heweather.com/s6/weather?location=" + URLEncoder.encode(AppUtils.stringRemoveSpace(mAddress_et.getText()), "UTF-8") + "&key=e213b21d1a25491ba625ce3d3d5dfc2d");
                    } else {
                        if (!TextUtils.equals(mEt_http.getText().toString().trim(), "")) {
                            url = new URL("https://free-api.heweather.com/s6/weather?location=" + URLEncoder.encode(mEt_http.getText().toString().trim(), "UTF-8") + "&key=e213b21d1a25491ba625ce3d3d5dfc2d");
                        } else {
                            url = new URL("https://free-api.heweather.com/s6/weather?location=" + URLEncoder.encode("杭州", "UTF-8") + "&key=e213b21d1a25491ba625ce3d3d5dfc2d");
                        }
                    }
                    LogUtils.d("httpActivity", url.toString());
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    LogUtils.d("httpActivity", result.toString());
                    showResult(result.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showResult(final String s) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTxt_http.setText(String.format("%s1111", s));
                //Log.d("x", s);
            }
        });
    }


    public void requestInfo(View view) {
        httpTest();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.request_weather_btn) {
            isRequsetWeather = true;
            httpTest();

        }
    }

    @Override
    protected void onDestroy() {
        AsyncUtils.cancelTask("test");
        super.onDestroy();
    }
}
