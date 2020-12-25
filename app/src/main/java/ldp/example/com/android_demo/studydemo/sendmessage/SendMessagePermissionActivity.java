package ldp.example.com.android_demo.studydemo.sendmessage;

import android.Manifest;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import ldp.example.com.android_demo.R;
import com.example.ldp.base_lib.base.BasePermissionActivity;

/**
 * created by ldp at 2018/8/3
 */
public class SendMessagePermissionActivity extends BasePermissionActivity {

    @ViewInject(R.id.phone_num)
    private EditText phone_number;
    @ViewInject(R.id.send_messages)
    private EditText send_message;
    @ViewInject(R.id.btn_send)
    private Button btn_send;
    @ViewInject(R.id.s_progressBar)
    private ProgressBar mProgressBar;
    private String mPhone;
    private String mMessages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendmessage);
        x.view().inject(this);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(10000);
//                    mProgressBar.setVisibility(View.GONE);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        btn_send.setOnClickListener(new MySmsOnClickListener());

    }

    private class MySmsOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            mPhone = phone_number.getText().toString().trim();
            mMessages = send_message.getText().toString().trim();
            if ("".equals(mMessages) || "".equals(mPhone)) {
                Toast.makeText(SendMessagePermissionActivity.this, "电话号码或短信内容不能为空", Toast.LENGTH_LONG).show();
            } else {
                performCodeWithPermission("发送短信权限", new PermissionCallback() {
                    @Override
                    public void hasPermission() {
                        sendMessages();
                    }

                    @Override
                    public void noPermission() {

                    }
                }, Manifest.permission.SEND_SMS);
            }
        }
    }

    private void sendMessages() {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(mPhone, null, mMessages, null, null);
    }
}
