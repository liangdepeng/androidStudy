package ldp.example.com.android_demo.studydemo.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;

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
                mAsyncTaskTest = new AsyncTaskTest();
                mAsyncTaskTest.execute();
                break;
            case R.id.cancel:
                mAsyncTaskTest.cancel(true);
                break;
            default:
                break;
        }
    }

    public class AsyncTaskTest extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            LogUtils.d("async","onPreExecute"+AppUtils.getThreadInfo());
            mProgressBar2.setProgress(0);
            mTextView.setText("准备开始...");
        }

        @Override
        protected String doInBackground(String... voids) {
            LogUtils.d("async","doInBackground"+AppUtils.getThreadInfo());

            int progress = 0;

            try {
                for (int i = 0; i < 100; i++) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar2.setProgress(values[0]);
            mTextView.setText("Loading..." + values[0] + " %");
        }

        @Override
        protected void onPostExecute(String aVoid) {
            LogUtils.d("async","onPostExecute"+AppUtils.getThreadInfo());
            mTextView.setText("完成");
        }

        @Override
        protected void onCancelled() {
            LogUtils.d("async","onCancelled"+AppUtils.getThreadInfo());
            super.onCancelled();
            mTextView.setText("已取消");
            mProgressBar2.setProgress(0);
        }
    }
}
