package ldp.example.com.android_demo.studydemo.yibuxiaoxi;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.ldp.base_lib.base.BaseMvpActivity;
import com.example.ldp.base_lib.bean.WeatherBean;

import ldp.example.com.android_demo.R;

import static ldp.example.com.android_demo.constants.Constants.WEATHER_URL;


/**
 * @author ldp
 */
public class IntentServiceMvpActivity extends BaseMvpActivity {

    @Override
    protected void initView() {
        final TextView textView = (TextView) findViewById(R.id.resultTxt);
        findViewById(R.id.startBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                Intent intent = new Intent(IntentServiceMvpActivity.this, MyIntentService.class);
                intent.putExtra("url", WEATHER_URL + "重庆");
                startService(intent);
            }
        });
        findViewById(R.id.startBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog();
                for (int i = 0; i < 10; i++) {
                    Intent intent = new Intent(IntentServiceMvpActivity.this, MyIntentService.class);
                    intent.putExtra("url", WEATHER_URL + "重庆");
                    startService(intent);
                }
            }
        });
        MyIntentService.setListener(new UpdateInfoListener() {
            @Override
            public void update(final WeatherBean bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(String.format("%s\n%s", Math.random() * 10000, bean.toString()));
                        hideProgressDialog();
                    }
                });
            }
        });
    }

    @Override
    protected void initPresenter() {
    }

    @Override
    protected void initData() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_intent_service;
    }

    @Override
    public void startRequestInfo() {

    }

    public interface UpdateInfoListener {
        void update(WeatherBean bean);
    }
}
