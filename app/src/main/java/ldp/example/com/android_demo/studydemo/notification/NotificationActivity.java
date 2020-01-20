package ldp.example.com.android_demo.studydemo.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Objects;

import ldp.example.com.android_demo.R;


/**
 * 需要有通知权限
 *
 * @author mini
 */
public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
    }

    private void initView() {

        findViewById(R.id.notification1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(NotificationActivity.this, "chat")
                        .setContentTitle("收到一条新消息")
                        .setContentText("在吗?")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_login_1))
                        .setAutoCancel(true)
                        .build();
                Objects.requireNonNull(manager).notify(1, notification);
            }
        });

        findViewById(R.id.notification2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager2 = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification2 = new NotificationCompat.Builder(NotificationActivity.this, "chat——2")
                        .setContentTitle("忽然又收到一条新消息")
                        .setContentText("你还在吗?")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_login_1))
                        .setAutoCancel(true)
                        .setColor(Color.BLUE)
                        .build();
                Objects.requireNonNull(manager2).notify(2, notification2);
            }
        });

    }
}
