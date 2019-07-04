package ldp.example.com.android_demo.studydemo.xml_;

import android.Manifest;
import android.os.Environment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.utils.BaseActivity;

public class XmlActivity extends BaseActivity {

    private Button mBtn_pull;
    private TextView mPull_txt;
    private EditText mEditText;
    private EditText mEditText2;
    private EditText mEditText3;
    private Button mBtn_setXml;
    private Button mBtn_pull_demo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml);
        //找到需要用的控件
        mBtn_pull = (Button) findViewById(R.id.btn_pull);
        mPull_txt = (TextView) findViewById(R.id.pull_txt);
        mEditText = (EditText) findViewById(R.id.editText);
        mEditText2 = (EditText) findViewById(R.id.editText2);
        mEditText3 = (EditText) findViewById(R.id.editText3);
        mBtn_setXml = (Button) findViewById(R.id.btn_setxml);
        mBtn_pull_demo = (Button) findViewById(R.id.btn_pull_demo);
        //按键监听
        mBtn_pull.setOnClickListener(new PullXmlOnClickListener());
        mBtn_setXml.setOnClickListener(new SetXmlOnClickListener());
        mBtn_pull_demo.setOnClickListener(new DemoSetOnClickListener());
    }

    /**
     * 解析xml文件
     */
    public void pull_xml() {
        try {
            //找到资产文件并打开
            InputStream is = getAssets().open("myinfo.xml");
            //xml文件解析器
            XmlPullParser xmlPullParser = Xml.newPullParser();
            //设置输入流和编码
            xmlPullParser.setInput(is, "utf-8");
            //解析xml文件，获取当前的事件类型
            int eventType = xmlPullParser.getEventType();
            String s = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                System.out.println(xmlPullParser.getEventType() + "-----" + xmlPullParser.getName() + "-----" + xmlPullParser.getText());
                String ss = xmlPullParser.getEventType() + "-----" + xmlPullParser.getName() + "-----" + xmlPullParser.getText();
                s += ss;
                eventType = xmlPullParser.next();
            }
            mPull_txt.setText(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成xml文件
     */
    private void setXml() {
        //获取输入文本
        final String s1 = mEditText.getText().toString().trim();
        final String s2 = mEditText2.getText().toString().trim();
        final String s3 = mEditText3.getText().toString().trim();

        //判断是否为空
        if (TextUtils.isEmpty(s1) || TextUtils.isEmpty(s2) || TextUtils.isEmpty(s3)) {
            Toast.makeText(XmlActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
        } else {
            //将字符串写入xml文档
            performCodeWithPermission("写文件", new PermissionCallback() {
                @Override
                public void hasPermission() {
                    setXmlData(s1, s2, s3);
                }

                @Override
                public void noPermission() {

                }
            }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void setXmlData(String s1, String s2, String s3) {

        // 1  创建一个xml文件的序列号器
        XmlSerializer serializer = Xml.newSerializer();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory(), "myinfo.xml"));
            // 2  设置文件输出和编码方式
            serializer.setOutput(fos, "utf-8");
            // 3  写入xml文件的头
            serializer.startDocument("utf-8", true);
            // 4  写info节点
            serializer.startTag(null, "info");
            // 5  写student节点
            serializer.startTag(null, "student");
            // 6  写属性
            serializer.attribute(null, "id", s3);
            // 7  写name
            serializer.startTag(null, "name");
            serializer.text(s1);
            serializer.endTag(null, "name");
            // 8 写age
            serializer.startTag(null, "age");
            serializer.text(s2);
            serializer.endTag(null, "age");

            serializer.endTag(null, "student");
            serializer.endTag(null, "info");
            serializer.endDocument();

            Toast.makeText(XmlActivity.this, "xml文件已生成", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 天气解析
     */
    private void getXmlData() {
        try {
            //找到xml文件
            InputStream fis = getAssets().open("getWeatherbyCityName.xml");
            //xml文件解析器
            XmlPullParser pullParser = Xml.newPullParser();
            //设置输入流和编码
            pullParser.setInput(fis, "utf-8");

            ArrayList<String> infos = new ArrayList<>();
            //解析xml文件，获取事件类型
            int eventType = pullParser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (TextUtils.equals(pullParser.getName(), "string")) {
                    String s = pullParser.nextText();
                    infos.add(s);
                }
                eventType = pullParser.next();
            }
            fis.close();
            String cityName = infos.get(0);
            String temp = infos.get(1);
            String weather = infos.get(2);
            String wind = infos.get(3);
            String weatherInfo = infos.get(4);

            mPull_txt.setText("城市名称 ：" + cityName + "\n温度 ：" + temp + "\n天气 ："
                    + weather + "\n风 ：" + wind + "\n天气情况 ：" + weatherInfo);
        } catch (IOException e) {

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private class PullXmlOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            pull_xml();
        }
    }

    private class SetXmlOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            setXml();
        }
    }

    private class DemoSetOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            getXmlData();
        }
    }


}
