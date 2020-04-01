package ldp.example.com.android_demo.studydemo.yibuxiaoxi;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.utils.BaseActivity;

/**
 * 避免 多次创建 和 销毁线程 可以用 HandlerThread
 *
 * @author ldp
 */
public class HandlerThreadActivity extends BaseActivity {

    private Handler mainHandler;
    private Handler workHandler;
    private MyHandlerThreadRVAdapter adapter;
    private HandlerThread handlerThread;
    private ArrayList<String> stringArrayList = new ArrayList<>();
    private int msgCount = 0;
    private int waitMsgCount = 0;
    private int finishMsgCount = 0;

    private TextView msgCountTv;
    private TextView waitCountTv;
    private TextView finishCountTv;
    private RecyclerView recyclerView;
    private Switch switchBtn;
    private EditText editText;
    private Button userSendPiLiangBtn;
    private LinearLayout piliangLayout;
    private Button sendMsgBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handlerthread);
        initView();

        handlerThread = new HandlerThread("handlerThreadTest");
        handlerThread.start();// 记得开启线程

        mainHandler = new Handler(Looper.getMainLooper());
        workHandler = new Handler(handlerThread.getLooper()) {
            /**
             * 在工作线程 （子线程 ）
             * @param msg 消息
             */
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 200) {
                    stringArrayList.add(String.valueOf(msg.obj));
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //runOnUiThread();
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            finishCountTv.setText(String.format(getString(R.string.finish_msg_count), ++finishMsgCount));
                            waitCountTv.setText(String.format(getString(R.string.wait_msg_count), msgCount - finishMsgCount));
                            recyclerView.scrollToPosition(stringArrayList.size() - 1);
                        }
                    });
                }
            }
        };

    }

    private void initView() {
        msgCountTv = (TextView) findViewById(R.id.msgCount);
        waitCountTv = (TextView) findViewById(R.id.waitingText);
        finishCountTv = (TextView) findViewById(R.id.finishText);
        switchBtn = findViewById(R.id.switchBtn);
        editText = findViewById(R.id.userSendCount);
        userSendPiLiangBtn = findViewById(R.id.userSendPiLiangBtn);
        piliangLayout = findViewById(R.id.pi_liang);
        sendMsgBtn = findViewById(R.id.sendMsgBtn);

        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    piliangLayout.setVisibility(View.VISIBLE);
                    sendMsgBtn.setVisibility(View.GONE);
                } else {
                    piliangLayout.setVisibility(View.GONE);
                    sendMsgBtn.setVisibility(View.VISIBLE);
                }
            }
        });

        userSendPiLiangBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = editText.getText().toString().trim();
                Integer value = Integer.valueOf(input);

                for (int i = 0; i < value; i++) {
                    msgCountTv.setText(String.format(getString(R.string.msg_count), ++msgCount));
                    waitCountTv.setText(String.format(getString(R.string.wait_msg_count), msgCount - finishMsgCount));
                    if (workHandler != null) {
                        Message message = Message.obtain();
                        StringBuilder builder = new StringBuilder();
                        builder.append("第").append(msgCount).append("消息，随机数：").append(Math.random() * 100000);
                        message.obj = builder.toString();
                        message.what = 200;
                        workHandler.sendMessage(message);
                    }
                }
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyHandlerThreadRVAdapter(this, stringArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        sendMsgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgCountTv.setText(String.format(getString(R.string.msg_count), ++msgCount));
                waitCountTv.setText(String.format(getString(R.string.wait_msg_count), msgCount - finishMsgCount));
                if (workHandler != null) {
                    Message message = Message.obtain();
                    StringBuilder builder = new StringBuilder();
                    builder.append("第").append(msgCount).append("消息，随机数：").append(Math.random() * 100000);
                    message.obj = builder.toString();
                    message.what = 200;
                    workHandler.sendMessage(message);
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }
}
