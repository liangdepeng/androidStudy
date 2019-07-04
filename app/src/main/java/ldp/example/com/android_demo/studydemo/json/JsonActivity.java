package ldp.example.com.android_demo.studydemo.json;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.utils.BaseActivity;

public class JsonActivity extends BaseActivity {

    private TextView mCreateJson_txt;
    private TextView mAnalysisJson_txt;
    private TextView mJsonArray;
    private TextView mJsonArray2;
    private File mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        mCreateJson_txt = (TextView) findViewById(R.id.json_txt1);
        mAnalysisJson_txt = (TextView) findViewById(R.id.json_txt2);
        mJsonArray = (TextView) findViewById(R.id.jsonArray_txt1);
        mJsonArray2 = (TextView) findViewById(R.id.jsonArray_txt2);

        mCreateJson_txt.setText(createJson());  //         {"id":10086,"name":"李白","age":20}
        mAnalysisJson_txt.setText(analysisJson());

        mJsonArray.setText(createJsonArray());

        //      {"students":[{"id":10000,"name":"诸葛亮","age":18},{"id":10001,"name":"刘备","age":20},{"id":10002,"name":"小乔","age":16}]}
        //     {"students": [{"id": 10000,  "name": "诸葛亮","age": 18 },
        //                   {"id": 10001,"name": "刘备","age": 20},
        //                    {"id": 10002,"name": "小乔", "age": 16}]}
        writeSDcard();
        analysisJsonArray();

    }


    /**
     * 创建简单Json数据
     */
    private String createJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", 10086);
            jsonObject.put("name", "李白");
            jsonObject.put("age", 20);
            System.out.println(jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 解析简单Json数据
     */
    private String analysisJson() {
        try {
            String json = "{\"id\":10086,\"name\":\"李白\",\"age\":20}";
            JSONObject jsonObject1 = new JSONObject(json);
            String id = jsonObject1.getString("id");
            String name = jsonObject1.getString("name");
            String age = jsonObject1.getString("age");
            System.out.println(id + " " + name + " " + age);
            return (id + " " + name + " " + age);
        } catch (JSONException e) {
            e.printStackTrace();
            return String.valueOf(0);
        }
    }

    /**
     * 创建简单Json数组数据
     */
    private String createJsonArray() {
        int[] ids = {10000, 10001, 10002};
        String[] names = {"诸葛亮", "刘备", "小乔"};
        int[] age = {18, 20, 16};
        JSONArray students = new JSONArray();
        for (int i = 0; i < ids.length; i++) {
            JSONObject person = new JSONObject();
            try {
                person.put("id", ids[i]);
                person.put("name", names[i]);
                person.put("age", age[i]);
                students.put(person);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("students", students);
            System.out.println(jsonObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    /**
     * 解析json数组
     */
    private void analysisJsonArray() {
        String jsonArray = "{\"students\": [{\"id\": 10000,  \"name\": \"诸葛亮\",\"age\": 18 }," +
                "{\"id\": 10001,\"name\": \"刘备\",\"age\": 20}," +
                "{\"id\": 10002,\"name\": \"小乔\", \"age\": 16}]}";
        try {
            JSONObject jsonObject = new JSONObject(jsonArray);
            JSONArray students = jsonObject.getJSONArray("students");

            ArrayList<JsonArrayBean> person = new ArrayList<>();

            FileOutputStream fos = new FileOutputStream(mFile);

            for (int i = 0; i < students.length(); i++) {
                JsonArrayBean jsonArrayBean = new JsonArrayBean();
                person.add(jsonArrayBean);

                JSONObject studentsJSONObject = students.getJSONObject(i);
                jsonArrayBean.setId(studentsJSONObject.getInt("id"));
                jsonArrayBean.setName(studentsJSONObject.getString("name"));
                jsonArrayBean.setAge(studentsJSONObject.getInt("age"));

                //写入sd卡文件 练习
                fos.write(String.valueOf(studentsJSONObject.getInt("id")).getBytes());
                fos.write("\r\n".getBytes());//换行
                fos.write(studentsJSONObject.getString("name").getBytes());
                fos.write("\r\n".getBytes());
                fos.write(String.valueOf(studentsJSONObject.getInt("age")).getBytes());
                fos.write("\r\n".getBytes());

            }
            fos.close();
            System.out.println(person);
            mJsonArray2.setText(person.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeSDcard() {
        performCodeWithPermission("写文件到sd卡", new PermissionCallback() {
            @Override
            public void hasPermission() {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    mFile = new File(Environment.getExternalStorageDirectory(), "my_students.txt");
                }
            }

            @Override
            public void noPermission() {

            }
        }, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public void toGson(View view) {
        startActivity(new Intent(this, GsonParseActivity.class));
    }
}
