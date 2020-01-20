package ldp.example.com.android_demo.studydemo.wrisesdfile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ldp.example.com.android_demo.R;


/**
 * SharedPreferences类，它是一个轻量级的存储类，特别适合用于保存软件配置参数。
 * SharedPreferences保存数据，其背后是用xml文件存放数据，
 * 文件存放在/data/data/<package name>/shared_prefs目录下
 *
 * @author mini
 */
public class SharedPreferenceActivity extends AppCompatActivity {

    private CheckBox mCk_1;
    private CheckBox mCk_2;
    private Button mBt_s;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shaerd_prefernces);

        mCk_1 = (CheckBox) findViewById(R.id.ck_1);
        mCk_2 = (CheckBox) findViewById(R.id.ck_2);
        mBt_s = (Button) findViewById(R.id.bt_s);

        mPreferences = getSharedPreferences(" setting", Context.MODE_PRIVATE);
        setting();
        mBt_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 清理SharedPreferences
                 */
                SharedPreferences.Editor edit = mPreferences.edit();
                edit.clear();
                edit.commit();
                Toast.makeText(SharedPreferenceActivity.this, "清理完毕", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setting() {
        boolean state1 = mPreferences.getBoolean("state1", false);
        mCk_1.setChecked(state1);
        mCk_1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mPreferences.edit();
                if (isChecked) {
                    editor.putBoolean("state1", true);
                    editor.commit();
                } else {
                    editor.putBoolean("state1", false);
                    editor.commit();
                }
                Toast.makeText(SharedPreferenceActivity.this, "checkbox_1状态已更新", Toast.LENGTH_SHORT).show();
            }
        });

        boolean state2 = mPreferences.getBoolean("state2", false);
        mCk_2.setChecked(state2);

        mCk_2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = mPreferences.edit();
                if (isChecked) {
                    editor.putBoolean("state2", true);
                    editor.commit();
                } else {
                    editor.putBoolean("state2", false);
                    editor.commit();
                }
                Toast.makeText(SharedPreferenceActivity.this, "checkbox_2状态已更新", Toast.LENGTH_SHORT).show();
            }
        });

        //        mCk_1.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                Toast.makeText(SharedPreferenceActivity.this, "点击", Toast.LENGTH_SHORT).show();
        //            }
        //        });
    }
}

