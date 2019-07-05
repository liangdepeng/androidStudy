package ldp.example.com.android_demo.studydemo.dialog;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import ldp.example.com.android_demo.MyApplication;
import ldp.example.com.android_demo.R;

import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_DARK;
import static android.app.AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
import static android.app.AlertDialog.THEME_HOLO_DARK;
import static android.app.AlertDialog.THEME_HOLO_LIGHT;
import static android.app.AlertDialog.THEME_TRADITIONAL;

public class DialogsActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isHasProgress = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialogs);

        Button btnAlertDialog = (Button) findViewById(R.id.AlertDialog);
        Button btnProgressDialog = (Button) findViewById(R.id.ProgressDialog);
        Button list_dialog = (Button) findViewById(R.id.list_dialog);
        Button single_choose_dialog = (Button) findViewById(R.id.single_choose_dialog);
        Button multi_choice_dialog = (Button) findViewById(R.id.multi_choice_dialog);
        findViewById(R.id.timePickerDialog).setOnClickListener(this);
        multi_choice_dialog.setOnClickListener(this);
        single_choose_dialog.setOnClickListener(this);
        list_dialog.setOnClickListener(this);
        btnAlertDialog.setOnClickListener(this);
        btnProgressDialog.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.AlertDialog:
                setNormalAlertDialog();
                break;
            case R.id.ProgressDialog:
                setProgressDialog();
                break;
            case R.id.list_dialog:
                setListDialog();
                break;
            case R.id.single_choose_dialog:
                setSingleChoiceDialog();
                break;
            case R.id.multi_choice_dialog:
                setMultiChoiceDialog();
                break;
            case R.id.timePickerDialog:
                setTimePickerDialog();
                break;
            default:
                break;
        }
    }

    private int i = 0;

    private void setTimePickerDialog() {
        // 5.0 之后默认
        //        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
        //            @Override
        //            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        //                showTips(hourOfDay + " : " + minute,1);
        //            }
        //        }, 0, 0, true);
        //        timePickerDialog.setTitle("时间选择");
        //        timePickerDialog.show();

        int[] styles = {THEME_DEVICE_DEFAULT_LIGHT, // 新版样式亮色主题
                THEME_DEVICE_DEFAULT_DARK, //新版样式暗色主题
                THEME_HOLO_DARK, // 旧版样式1
                THEME_HOLO_LIGHT, // 旧版样式2
                THEME_TRADITIONAL}; // 旧版样式3

        if (i == 5) { i = 0; }

        new TimePickerDialog(this, styles[i++], new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                showTips(hourOfDay + " : " + minute, 1);
            }
        }, 0, 0, true).show();
    }

    private void setMultiChoiceDialog() {
        final String[] items = {"111", "222", "333", "444", "555"};
        final boolean[] checkStates = {false, false, false, false, false};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("多选对话框");
        builder.setMultiChoiceItems(items, checkStates, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    checkStates[which] = true;
                }
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < 5; i++) {
                    if (checkStates[i]) {
                        stringBuilder.append(items[i]).append("  ");
                    }
                }
                showTips(stringBuilder.toString() + " is selected", 0);
            }
        });
        builder.show();
    }

    private void setSingleChoiceDialog() {
        final String[] items = {"111", "222", "333", "444", "555"};
        final int[] userChoice = {0};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("单选对话框");
        builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                userChoice[0] = which;
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showTips(String.valueOf(items[userChoice[0]]) + " is selected", 0);
            }
        });
        builder.show();
    }

    private void setListDialog() {
        final String[] items = {"111", "222", "333", "444", "555"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("listDialog");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DialogsActivity.this, items[which] + " is clicked", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void setProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("progress_dialog");
        if (isHasProgress) {
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int progress = 0;
                    while (progress < 100) {
                        try {
                            Thread.sleep(50);
                            // 在子线程 方法内部会判断 执行post 到主线程更新
                            progressDialog.setProgress(progress++);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    progressDialog.dismiss();
                }
            }).start();
        } else {
            progressDialog.setMessage("hello");
            progressDialog.show();
        }
        isHasProgress = !isHasProgress;
    }

    private void setNormalAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("alert_dialog");
        alertDialog.setMessage("hello");
        alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    /**
     * @param msg  提示信息
     * @param time 显示时间
     *             <p>
     *             private int LONG = 1;
     *             private int SHORT = 0;
     */
    private void showTips(String msg, int time) {
        Toast.makeText(MyApplication.getAppContent(), msg, time).show();
    }
}
