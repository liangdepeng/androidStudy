package com.example.ldp.base_lib.http;

import android.os.AsyncTask;
import android.os.Handler;

import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by Da Peng at 2019/6/21
 * <p>
 * * 安卓简单原生网络请求
 */
public class HttpRequestInfo {
    // 获取当前设备·CPU数·
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    private static final long ALIVE_TIME = 5L;

    private onHttpResponseListner mListener;

    public static final Executor threadPoolExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());

    private final Handler mHandler = new Handler();

    private HttpURLConnection connection = null;

    public HttpRequestInfo(onHttpResponseListner onHttpResponseListner) {
        this.mListener = onHttpResponseListner;
    }

    public void requestInfo(final String requestUrl, final Class genericClass) {


        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                httpGetRequest(requestUrl, genericClass);
            }

        };
        threadPoolExecutor.execute(runnable);
    }

    public void testAsyncTaskHttpRequest(final String requestUrl, final Class genericClass){
        HttpAsyncTaskTest httpAsyncTaskTest = new HttpAsyncTaskTest();
        httpAsyncTaskTest.execute(requestUrl,(Object) genericClass);
    }

    private void httpGetRequest(String requestUrl, Class genericClass) {
        try {
            LogUtils.e("url",requestUrl);
            URL url = new URL(requestUrl);
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

            final Object object = new Gson().fromJson(result.toString(), genericClass);
            //String threadName = Thread.currentThread().getName();
            LogUtils.e("thread", AppUtils.getThreadInfo());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.e("thread", AppUtils.getThreadInfo());
                    mListener.onSuccess(object);
                }
            }, 2000);

        } catch (Exception e) {
            mListener.onFailed(e);
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 利用AsyncTask 实现异步网络请求
     */
    public class HttpAsyncTaskTest extends AsyncTask<Object, Integer, Object> {

        // 方法1：onPreExecute（）
        // 作用：执行 线程任务前的操作
        // 注：根据需求复写
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 方法2：doInBackground（）
        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        // 注：必须复写，从而自定义线程任务
        @Override
        protected Object doInBackground(Object... objects) {
            LogUtils.e("thread", "doInBackground + "+AppUtils.getThreadInfo());
            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(objects[0].toString());
                connection = (HttpURLConnection) url.openConnection();
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
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
            LogUtils.e("obj",objects[0].toString() +"  " + objects[1].toString());
            final Object object = new Gson().fromJson(result.toString(), ((Class) objects[1]));
            return object;
        }

        // 方法3：onProgressUpdate（）
        // 作用：在主线程 显示线程任务执行的进度
        // 注：根据需求复写

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        // 方法4：onPostExecute（）
        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        // 注：必须复写，从而自定义UI操作

        @Override
        protected void onPostExecute(Object object) {
            mListener.onSuccess(object);
        }

        // 方法5：onCancelled()
        // 作用：将异步任务设置为：取消状态

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}

//        new Thread(new Runnable() {
//            HttpURLConnection connection = null;
//            @Override
//            public void run() {
//                try {
//                    URL url = new URL(requestUrl);
//                    connection = (HttpURLConnection) url.openConnection();
//                    connection.setRequestMethod("GET");
//                    connection.setReadTimeout(8000);
//                    connection.setConnectTimeout(8000);
//                    InputStream inputStream = connection.getInputStream();
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//                    StringBuilder result = new StringBuilder();
//                    String line;
//                    while ((line=reader.readLine())!=null){
//                        result.append(line);
//                    }
//                    Object object = new Gson().fromJson(result.toString(),genericClass);
//                    //showResult(result.toString());
//                    mListener.onSuccess(object);
//                } catch (Exception e) {
//                    mListener.onFailed(e);
//                    e.printStackTrace();
//                }finally {
//                    connection.disconnect();
//                }
//            }
//        }).start();