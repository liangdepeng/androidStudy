package ldp.example.com.android_demo.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ldp.base_lib.utils.LogUtils;

import ldp.example.com.android_demo.R;

/**
 * @author mini
 */
public class TestServiceActivity extends AppCompatActivity {

    private TestBindService testBindService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tset_service);
        initView();
    }

    private void initView() {

        /**
         * StartService 方式开启服务
         */
        final Intent intent = new Intent(TestServiceActivity.this, TestStartService.class);
        findViewById(R.id.startServiceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);
            }
        });

        findViewById(R.id.stopStartServiceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });


        /**
         * BindService 方式绑定服务 解绑服务
         */

        final Intent intent1 = new Intent(this, TestBindService.class);
        findViewById(R.id.bindServiceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e(".", "服务开始绑定！");
                bindService(intent1, serviceConnection, Service.BIND_AUTO_CREATE);
            }
        });
        findViewById(R.id.unbindServiceBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testBindService != null) {
                    unbindService(serviceConnection);
                    testBindService = null;
                }
            }
        });


        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LogUtils.e("onServiceConnected", "onServiceConnectedSuccess");
                TestBindService.LocalBinder binder = (TestBindService.LocalBinder) service;
                testBindService = binder.getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                LogUtils.e("onServiceDisconnected", "onServiceDisconnected");
            }
        };

        findViewById(R.id.getDataBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testBindService != null) {
                    Toast.makeText(TestServiceActivity.this, testBindService.getBindInfo(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(TestServiceActivity.this, "服务未绑定!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
