package ldp.example.com.android_demo.studydemo.callphone;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.ldp.base_lib.base.BaseMvpActivity;
import com.example.ldp.base_lib.base.ViewHolder;

import ldp.example.com.android_demo.R;

public class DialogMvpActivity extends BaseMvpActivity {

    /**
     * 测试 {@link ViewHolder}
     */
    private ViewHolder holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 点击外部 finish()
        setFinishOnTouchOutside(false);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initView() {
        holder.setText(R.id.text1, "对话框式的活动")
                .setViewAlpha(R.id.text1, (float) 0.5)
                .setText(R.id.text2, "lalala")
                .setOnClickListener(R.id.button3, new FinishListener())
                .setTextColor(R.id.text1, Color.parseColor("#ffffff"))
                .setImagineByNetUrl(this, R.id.imageView2, "", R.drawable.ic_login_1)
                .setImagineByNetUrl(this, R.id.imageView, "http://img5.mtime.cn/mg/2019/06/29/002009.16684021_120X90X4.jpg", R.drawable.ic_launcher_background);
    }

    @Override
    protected void initData() {
        holder = ViewHolder.init(getContentView());
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_dialog;
    }

    @Override
    public void startRequestInfo() {

    }

    private class FinishListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    }
}
