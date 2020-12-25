package ldp.example.com.android_demo.studydemo.wrisesdfile;

import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;

import ldp.example.com.android_demo.R;
import com.example.ldp.base_lib.base.BasePermissionActivity;

/**
 * created by ldp at 2018/8/10
 */
public class WriteSDfilePermissionActivity extends BasePermissionActivity implements View.OnClickListener {

    @ViewInject(R.id.nei_txt)
    private TextView nei_txt;
    @ViewInject(R.id.wai_txt)
    private TextView wai_txt;
    @ViewInject(R.id.write)
    private Button btn_write;
    @ViewInject(R.id.btn_del_sd_file)
    private Button btn_del_sd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_sd_file);
        x.view().inject(this);

        File dataFile = Environment.getDataDirectory();
        File sdFile = Environment.getExternalStorageDirectory();

        long dataSize = dataFile.getTotalSpace();
        long sdSize = sdFile.getTotalSpace();

        nei_txt.setText("内部存储空间 ：" + Formatter.formatFileSize(this, dataSize));
        wai_txt.setText("外部存储空间 ：" + Formatter.formatFileSize(this, sdSize));
        btn_del_sd.setEnabled(false);//未相关还权限，初始化按键不可被点击
        btn_write.setOnClickListener(this);
        btn_del_sd.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == btn_write) {
            performCodeWithPermission("写入文件到sd卡权限", new PermissionCallback() {
                @Override
                public void hasPermission() {
                    //检查是否有sd卡，是否挂载
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        File sdspace = Environment.getExternalStorageDirectory();
                        if (sdspace.getFreeSpace() > 5 * 1024 * 1024) {
                            //写文件
                            File file = new File(Environment.getExternalStorageDirectory(), "Test_test.3gp");
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                byte[] bytes = new byte[1024];
                                for (int i = 0; i < 5 * 1024; i++) {
                                    fos.write(bytes);
                                }
                                fos.close();
                                Toast.makeText(WriteSDfilePermissionActivity.this,
                                        "写入文件完成", Toast.LENGTH_SHORT).show();
                                btn_del_sd.setEnabled(true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(WriteSDfilePermissionActivity.this,
                                    "sd卡空间不足", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(WriteSDfilePermissionActivity.this,
                                "sd卡被拔出或者没有挂载", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void noPermission() {
                    Toast.makeText(WriteSDfilePermissionActivity.this,
                            "没有相关权限", Toast.LENGTH_SHORT).show();
                }
            }, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else if (v == btn_del_sd) {
            //删除文件
            File file = new File(Environment.getExternalStorageDirectory(), "Test_test.3gp");
            if (file.exists()){
                file.delete();
                Toast.makeText(WriteSDfilePermissionActivity.this,"文件删除成功",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(WriteSDfilePermissionActivity.this,"文件不存在",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
