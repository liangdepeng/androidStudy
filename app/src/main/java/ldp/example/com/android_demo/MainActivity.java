package ldp.example.com.android_demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import ldp.example.com.android_demo.studydemo.callphone.CallphonePermissionActivity;
import ldp.example.com.android_demo.studydemo.contentprovider.ContentProviderPermissionActivity;
import ldp.example.com.android_demo.studydemo.dialog.DialogsActivity;
import ldp.example.com.android_demo.studydemo.http.HttpActivity;
import ldp.example.com.android_demo.studydemo.json.JsonPermissionActivity;
import ldp.example.com.android_demo.studydemo.kotlin.KotlinVideoPlayerActivity;
import ldp.example.com.android_demo.studydemo.qqlogin.QQLogin_Activity;
import ldp.example.com.android_demo.studydemo.sendmessage.SendMessagePermissionActivity;
import ldp.example.com.android_demo.studydemo.sqllitedemo.SqlActivity;
import ldp.example.com.android_demo.studydemo.wrisesdfile.SharedPreferenceActivity;
import ldp.example.com.android_demo.studydemo.wrisesdfile.WriteActivity;
import ldp.example.com.android_demo.studydemo.wrisesdfile.WriteSDfilePermissionActivity;
import ldp.example.com.android_demo.studydemo.xml_.XmlPermissionActivity;
import ldp.example.com.android_demo.studydemo.zidingyikj.circleActivity;
import ldp.example.com.android_demo.weather.activity.WeatherWelcomeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.call_phone)
    private Button btn_call_phone;
    @ViewInject(R.id.send_message)
    private Button btn_send_message;
    @ViewInject(R.id.zdy_view)
    private Button btn_zdy_view;
    @ViewInject(R.id.Login)
    private Button btn_login;
    @ViewInject(R.id.cun_chu)
    private Button btn_cun_chu;
    @ViewInject(R.id.btn_txt)
    private Button btn_txt;
    @ViewInject(R.id.btn_xml_start)
    private Button btn_xml;
    @ViewInject(R.id.btn_SharedPreferences)
    private Button btn_sharedpreferences;
    @ViewInject(R.id.bt_json)
    private Button btn_json;
    @ViewInject(R.id.btn_sqlite)
    private Button btn_SQLite;
    @ViewInject(R.id.btn_content_provider)
    private Button btn_content_provider;
    @ViewInject(R.id.btn_dialog)
    private Button btn_dialog;
    @ViewInject(R.id.btn_http)
    private Button btn_http;
    @ViewInject(R.id.btn_kotlin)
    private Button btn_kotlin;
    @ViewInject(R.id.btn_weather)
    private Button btn_weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_);
        x.view().inject(this);

        btn_call_phone.setOnClickListener(this);
        btn_send_message.setOnClickListener(this);
        btn_zdy_view.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_cun_chu.setOnClickListener(this);
        btn_txt.setOnClickListener(this);
        btn_xml.setOnClickListener(this);
        btn_sharedpreferences.setOnClickListener(this);
        btn_json.setOnClickListener(this);
        btn_SQLite.setOnClickListener(this);
        btn_content_provider.setOnClickListener(this);
        btn_dialog.setOnClickListener(this);
        btn_http.setOnClickListener(this);
        btn_kotlin.setOnClickListener(this);
        btn_weather.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);//menu菜单
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //menu菜单选中监听处理
        switch (item.getItemId()) {
            default:
                break;
            case R.id.item1:
                Toast.makeText(this, "click item1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this, "click item2", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.call_phone://打电话
                start_call_phone();
                break;
            case R.id.send_message://发短信
                start_send_message();
                break;
            case R.id.zdy_view://view
                start_zdy_view();
                break;
            case R.id.Login://qq本地模拟登陆
                start_login();
                break;
            case R.id.cun_chu://存储
                start_cun_chu();
                break;
            case R.id.btn_txt://简单文件操作
                start_txt();
                break;
            case R.id.btn_xml_start://xml文件
                start_xml();
                break;
            case R.id.btn_SharedPreferences://sharedpreferences
                start_sharedpreferences_Activity();
                break;
            case R.id.bt_json://json
                startJson();
                break;
            case R.id.btn_sqlite:
                startSQLite();
                break;
            case R.id.btn_content_provider:
                startContentProvider();
                break;
            case R.id.btn_dialog:
                startDialog();
                break;
            case R.id.btn_http:
                startHttp();
                break;
            case R.id.btn_kotlin:
                startActivity(new Intent(MainActivity.this,
                        KotlinVideoPlayerActivity.class));
                break;
            case R.id.btn_weather:
                startActivity(new Intent(MainActivity.this,
                        WeatherWelcomeActivity.class));
            default:
                break;
        }
    }

    private void startHttp() {
        startActivity(new Intent(MainActivity.this,
                HttpActivity.class));
    }

    private void startDialog() {
        startActivity(new Intent(MainActivity.this,
                DialogsActivity.class));
    }

    private void startContentProvider() {
        startActivity(new Intent(MainActivity.this,
                ContentProviderPermissionActivity.class));
    }

    private void startSQLite() {
        startActivity(new Intent(MainActivity.this,
                SqlActivity.class));
    }

    private void startJson() {
        startActivity(new Intent(MainActivity.this,
                JsonPermissionActivity.class));
    }

    private void start_sharedpreferences_Activity() {
        startActivity(new Intent(MainActivity.this,
                SharedPreferenceActivity.class));
    }

    private void start_xml() {
        startActivity(new Intent(MainActivity.this,
                XmlPermissionActivity.class));
    }

    private void start_txt() {
        startActivity(new Intent(MainActivity.this,
                WriteActivity.class));
    }

    private void start_cun_chu() {
        startActivity(new Intent(MainActivity.this,
                WriteSDfilePermissionActivity.class));
    }

    private void start_login() {
        startActivity(new Intent(MainActivity.this,
                QQLogin_Activity.class));
    }

    private void start_zdy_view() {
        Intent intent = new Intent(MainActivity.this,
                circleActivity.class);
        startActivity(intent);
    }

    private void start_send_message() {
        Intent intent = new Intent(MainActivity.this,
                SendMessagePermissionActivity.class);
        startActivity(intent);
    }

    private void start_call_phone() {
        Intent intent = new Intent(MainActivity.this,
                CallphonePermissionActivity.class);
        intent.putExtra("tocallphone", "hello");
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 100:
                if (resultCode == RESULT_OK) {
                    String s = data.getStringExtra("return");
                    Log.d("result", s);
                }
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {

        System.out.println(2L);

        int num = 2147483647;
        long temp = num + 2L;
        System.out.println(temp);


        int num2 = 2147483647;
        num2 += 2L;
        System.out.println(num2);
    }
}
