package ldp.example.com.android_demo.studydemo.callphone;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.utils.BaseActivity;

public class CallphoneActivity extends BaseActivity {

    @ViewInject(R.id.btn_call_phone)
    private Button call_phone;
    @ViewInject(R.id.phone_number)
    private EditText number;
    @ViewInject(R.id.dialog)
    private Button btn_dialog;

    private String mPhone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callphone);
        Log.d("activity", "onCreate()");
        x.view().inject(this);

        Intent_data();

        call_phone.setOnClickListener(new MyCallPhoneOnClickListener());
        btn_dialog.setOnClickListener(new MyDialogOnClickListener());
    }

    /**
     * intent传递数据
     */
    private void Intent_data() {
        Intent intent = getIntent();
        String s = intent.getStringExtra("tocallphone");
        Log.d("call", s);

    }


    private class MyCallPhoneOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mPhone_number = number.getText().toString().trim();
            if ("".equals(mPhone_number)) {
                Toast.makeText(CallphoneActivity.this, "电话号码不能为空", Toast.LENGTH_LONG).show();
            } else {
                /**
                 * 运行时权限
                 */
                performCodeWithPermission("打电话权限", new PermissionCallback() {
                    @Override
                    public void hasPermission() {
                        callPhone();
                    }

                    @Override
                    public void noPermission() {

                    }
                }, Manifest.permission.CALL_PHONE);
            }

        }
    }

    /**
     * Intent.ACTION_CALL -> 直接调用系统底层的打电话功能的API，不经过手机的拨号界面直接去拨打电话
     * <p>
     * Intent.ACTION_DIAL -> 直接调用系统底层的拨号功能，会打开手机的拨号界面，再次点击拨号按钮才真正开始拨打电话
     */
    public void callPhone() {
        //创建一个意图对象
        Intent intent = new Intent();
        //设置动作
        intent.setAction(Intent.ACTION_DIAL);
        //指定的动作的数据
        intent.setData(Uri.parse("tel://" + mPhone_number));
        //开启打电话的界面
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activity", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activity", "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity", "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("activity", "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity", "onDestroy()");

    }

    /**
     * 手机返回键   intent回传数据
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("return", "收到hello");
        setResult(RESULT_OK, intent);
        finish();
    }

    private class MyDialogOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(CallphoneActivity.this, DialogActivity.class);
            startActivity(intent);
        }
    }
}
