package ldp.example.com.android_demo.studydemo.yibuxiaoxi;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;

import java.lang.ref.WeakReference;

import ldp.example.com.android_demo.R;

public class AsyncTaskActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar mProgressBar;
    private ProgressBar mProgressBar2;
    private Button mStart;
    private Button mCancel;
    private AsyncTaskTest mAsyncTaskTest;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);
        initView();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAsyncTaskTest.cancel(true);
    }

    private void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mProgressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mStart = (Button) findViewById(R.id.start);
        mCancel = (Button) findViewById(R.id.cancel);
        mTextView = (TextView) findViewById(R.id.text2);

        mStart.setOnClickListener(this);
        mCancel.setOnClickListener(this);

        mProgressBar2.setMax(100);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                mAsyncTaskTest = new AsyncTaskTest(AsyncTaskActivity.this);
                mAsyncTaskTest.execute();
                break;
            case R.id.cancel:
                if (mAsyncTaskTest != null)
                    mAsyncTaskTest.cancel(true);
                break;
            default:
                break;
        }
    }

    /**
     * 静态内部类 + 弱引用Activity
     */
    static class AsyncTaskTest extends AsyncTask<String, Integer, String> {

        private WeakReference<Activity> activityWeakReference;

        AsyncTaskTest(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        private AsyncTaskActivity getActivity() {
            AsyncTaskActivity asyncTaskActivity;
            asyncTaskActivity = (AsyncTaskActivity) activityWeakReference.get();
            return asyncTaskActivity;
        }

        @Override
        protected void onPreExecute() {
            LogUtils.d("async", "onPreExecute" + AppUtils.getThreadInfo());
            if (getActivity() != null) {
                getActivity().taskStart();
            }
        }


        @Override
        protected String doInBackground(String... voids) {
            LogUtils.d("async", "doInBackground" + AppUtils.getThreadInfo());

            int progress = 0;

            try {
                for (int i = 0; i < 100; i++) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (getActivity() != null) {
                getActivity().progressUpdate(values[0]);
            }
        }

        @Override
        protected void onPostExecute(String aVoid) {
            LogUtils.d("async", "onPostExecute" + AppUtils.getThreadInfo());
            if (getActivity() != null) {
                getActivity().postResultFinish();
            }

        }

        @Override
        protected void onCancelled() {
            LogUtils.d("async", "onCancelled" + AppUtils.getThreadInfo());
            super.onCancelled();
            if (getActivity() != null) {
                getActivity().taskCancel();
            }

        }
    }

    private void taskCancel() {
        mTextView.setText("已取消");
        mProgressBar2.setProgress(0);
    }

    private void postResultFinish() {
        mTextView.setText("完成");
    }

    private void progressUpdate(Integer value) {
        mProgressBar2.setProgress(value);
        mTextView.setText("Loading..." + value + " %");
    }

    private void taskStart() {
        mProgressBar2.setProgress(0);
        mTextView.setText("准备开始...");
    }
}
