package ldp.example.com.android_demo.studydemo.testcrash;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ldp.example.com.android_demo.R;

public class TestCrashActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_crash);
        initView();
    }

    private void initView() {
        findViewById(R.id.null_ex_btn).setOnClickListener(this);
        findViewById(R.id.out_index_ex_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == null) return;
        int id = v.getId();
        if (id == R.id.null_ex_btn) {
            String str = null;
            String nullStr = str.toString();
        } else if (id == R.id.out_index_ex_btn) {
            char[] chars = "12345".toCharArray();
            char aChar = chars[10];
        }
    }
}