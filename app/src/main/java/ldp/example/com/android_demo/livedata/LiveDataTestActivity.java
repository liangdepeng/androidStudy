package ldp.example.com.android_demo.livedata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ldp.base_lib.bean.WeatherBean;
import com.example.ldp.base_lib.utils.LogUtils;

import java.util.ArrayList;

import ldp.example.com.android_demo.R;

public class LiveDataTestActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private TestViewModel mTestViewModel;
    private TestViewModel2 mTestViewModel2;
    private TextView mLive_data_txt;
    private ArrayList<WeatherBean> mList = new ArrayList<>();
    private String cityName = "杭州";
    private TextView mLive_data_txt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_data_test);
        mLive_data_txt = (TextView) findViewById(R.id.live_data_txt);
        mLive_data_txt2 = (TextView) findViewById(R.id.live_data_txt2);
        initData1();
        initData2();
    }

    private void initData1() {
        // 初始化 自定义 mTestViewModel
        mTestViewModel = new TestViewModel();
        // 添加被观察数据变化，数据变化做出UI更新操作（onStart、onResume、onPause可以更新数据）
        mTestViewModel.getDatas().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                LogUtils.d(TAG,s + "live_data_changed");
                mLive_data_txt2.setText(s);
            }
        });

        mTestViewModel.getLiveDatas(mTestViewModel.getDatas(), new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {

            }
        });
    }

    private void initData2() {
        // 继承 ViewModel 初始化 mTestViewModel2  (并且示例带参数的实现)
        mTestViewModel2 = ViewModelProviders.of(this,
                new TestViewModel2.TestViewModelFactory(cityName))
                .get(TestViewModel2.class);

        // 添加被观察数据变化，数据变化做出UI更新操作
        mTestViewModel2.getData().observe(this, new Observer<WeatherBean>() {
            @Override
            public void onChanged(@Nullable WeatherBean weatherBean) {
                if (weatherBean != null) {
                    if (weatherBean.getResult() == null) {
                        mLive_data_txt.setText(weatherBean.getMsg());
                    } else {
                        mLive_data_txt.setText(weatherBean.toString());
                    }

                }
            }
        });
    }

    public void refresh(View view) {
        mTestViewModel2.requestData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTestViewModel.getDatas().setValue(" --onStart--"); //经验证可收到更新
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTestViewModel.getDatas().setValue(" --onResume--"); //经验证可收到更新
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTestViewModel.getDatas().setValue(" --onPause--"); //经验证可收到更新
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTestViewModel.getDatas().setValue(" --onStop--");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mTestViewModel.getDatas().setValue(" --onRestart--");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTestViewModel.getDatas().setValue(" --onDestroy--");
    }

    public void startFragmentActivity(View view) {
        startActivity(new Intent(this,FragmentTestActivity.class));
    }
}
