package ldp.example.com.android_demo.studydemo.wrisesdfile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import ldp.example.com.android_demo.R;

public class WriteActivity extends AppCompatActivity {

    private TextView mTxt;
    private EditText mEt_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mTxt = findViewById(R.id.txt_show);
        mEt_txt = findViewById(R.id.et_txt);
        // writeFile();
        // read_file();
    }

    public void read_file(View view) {

        /**
         * 读取文件
         */
        try {
            FileInputStream fis = openFileInput("info_.txt");
            byte[] bytes = new byte[1024];
            int len;
            while ((len = fis.read(bytes)) != -1) {
                System.out.println("content " + new String(bytes, 0, len));
                mTxt.setText("读取的文件内容 ：" + new String(bytes, 0, len));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            mTxt.setText("");
            Toast.makeText(WriteActivity.this, "文件不存在，请先写入文件", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFile(View view) {
        /**
         * 创建一个文件
         */
        try {
            String s = mEt_txt.getText().toString().trim();
            if (s.equals("")) {
                Toast.makeText(WriteActivity.this, "写入内容不能为空", Toast.LENGTH_SHORT).show();
            } else {
                FileOutputStream fos = openFileOutput("info_.txt", Context.MODE_PRIVATE);
                fos.write(s.getBytes());
                fos.close();
                Toast.makeText(WriteActivity.this, "写入文件完成", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteFile(View view) {
        File file = new File(getFilesDir(), "info_.txt");
        if (file.exists()) {
            file.delete();
            Log.d("xxx", "删除成功");
            Toast.makeText(WriteActivity.this, "文件删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(WriteActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
        }

    }
}
