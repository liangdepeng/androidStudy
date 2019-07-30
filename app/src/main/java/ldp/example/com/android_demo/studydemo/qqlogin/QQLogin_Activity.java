package ldp.example.com.android_demo.studydemo.qqlogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.kotlin.KotlinVideoPlayerActivity;

/**
 * @author mini
 */
public class QQLogin_Activity extends AppCompatActivity implements View.OnClickListener {

    @ViewInject(R.id.user_id)
    private EditText et_user_id;
    @ViewInject(R.id.user_mm)
    private EditText et_user_mm;
    @ViewInject(R.id.user_check)
    private CheckBox ck_user_check;
    @ViewInject(R.id.user_login)
    private Button btn_login;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqlogin_);
        x.view().inject(this);

        //readFile();

        readSp();
        btn_login.setOnClickListener(this);

        ck_user_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    /**
                     * 若checkbox没有被选中，则删除保存的文件和 SharedPreferences
                     */
                    File file = new File(QQLogin_Activity.this.getFilesDir(), "info.txt");
                    if (file.exists() && file.length() > 0) {
                        file.delete();
                    }

                    SharedPreferences.Editor editor = sp.edit();
                    editor.clear();
                    editor.apply();
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        String user_name = et_user_id.getText().toString().trim();
        String user_mm = et_user_mm.getText().toString().trim();


        if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(user_mm)) {
            Toast.makeText(QQLogin_Activity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
        } else {
            if (ck_user_check.isChecked()) {
                try {
                    /**
                     * 1   ------    保存数据到文件
                     */
                    File file = new File(this.getFilesDir(), "info.txt");
                    FileOutputStream os = new FileOutputStream(file);
                    String info = user_name + "##" + user_mm;
                    os.write(info.getBytes());
                    os.close();
                    /**
                     * 2  -------    保存数据到 SharedPreferences
                     */
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("qq", user_name);
                    editor.putString("mm", user_mm);
                    editor.apply();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //模拟登陆
            if (TextUtils.equals(user_name, "ldp12580") && TextUtils.equals(user_mm, "123456")) {
                Toast.makeText(QQLogin_Activity.this, "登录成功", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, KotlinVideoPlayerActivity.class));
                finish();
            } else {
                Toast.makeText(QQLogin_Activity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void readFile() {
        File file = new File(this.getFilesDir(), "info.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream in = new FileInputStream(file);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                //读取一行
                String info = reader.readLine();

                //分隔符分割
                String userMame2 = info.split("##")[0];
                String userMm2 = info.split("##")[1];

                et_user_id.setText(userMame2);
                et_user_mm.setText(userMm2);
                ck_user_check.setChecked(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readSp() {
        sp = getSharedPreferences("info2", Context.MODE_PRIVATE);
        et_user_id.setText(sp.getString("qq", ""));
        et_user_mm.setText(sp.getString("mm", ""));
        if (!"".equals(et_user_id.getText().toString().trim())) {
            ck_user_check.setChecked(true);
        }
    }

}
