package ldp.example.com.android_demo.studydemo.sqllitedemo;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.studydemo.sqllitedemo.dao.StudentDao;

public class SqlActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText wtStudentName;
    private EditText wtStudentId;
    private Button btnAdd;
    private Button btnDelete;
    private Button btnUpdate;
    private Button btnQuery;
    private TextView resultTxt;
    private StudentDao mStudentDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
//        Sqlite1 sqlite1 = new Sqlite1(this);
//        sqlite1.getWritableDatabase();
        findViews();
        mStudentDao = new StudentDao(this);
    }


    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-11-02 14:21:46 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        wtStudentName = (EditText)findViewById( R.id.wt_student_name );
        wtStudentId = (EditText)findViewById( R.id.wt_student_id );
        btnAdd = (Button)findViewById( R.id.btn_add );
        btnDelete = (Button)findViewById( R.id.btn_delete );
        btnUpdate = (Button)findViewById( R.id.btn_update );
        btnQuery = (Button)findViewById( R.id.btn_query );
        resultTxt = (TextView)findViewById( R.id.result_txt );

        btnAdd.setOnClickListener( this );
        btnDelete.setOnClickListener( this );
        btnUpdate.setOnClickListener( this );
        btnQuery.setOnClickListener( this );
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2018-11-02 14:21:46 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == btnAdd ) {
            // 添加一条联系人信息
            String name = wtStudentName.getText().toString().trim();
            String id = wtStudentId.getText().toString().trim();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(id)){
                Toast.makeText(this,"添加信息不能为空",Toast.LENGTH_SHORT).show();
            }else {
                mStudentDao.SQLite_add(name,id);
                Toast.makeText(this,"添加信息成功",Toast.LENGTH_SHORT).show();
            }
        } else if ( v == btnDelete ) {
            // 删除一条信息
            String name = wtStudentName.getText().toString().trim();
            if (TextUtils.isEmpty(name)){
                Toast.makeText(this,"信息不能为空",Toast.LENGTH_SHORT).show();
            }else {
                mStudentDao.SQLite_delete(name);
                Toast.makeText(this,"删除信息成功",Toast.LENGTH_SHORT).show();
            }
        } else if ( v == btnUpdate ) {
            // 更改信息
            String name = wtStudentName.getText().toString().trim();
            String id = wtStudentId.getText().toString().trim();
            if (TextUtils.isEmpty(name)||TextUtils.isEmpty(id)){
                Toast.makeText(this,"信息不能为空",Toast.LENGTH_SHORT).show();
            }else {
                mStudentDao.SQLite_update(name,id);
                Toast.makeText(this,"信息更新成功",Toast.LENGTH_SHORT).show();
            }
        } else if ( v == btnQuery ) {
            // 查询信息
            String name = wtStudentName.getText().toString().trim();
            if (TextUtils.isEmpty(name)){
                Toast.makeText(this,"信息不能为空",Toast.LENGTH_SHORT).show();
            }else {

                if (mStudentDao.SQLite_query(name)==null){
                    Toast.makeText(this,"信息不存在",Toast.LENGTH_SHORT).show();
                }else {
                    resultTxt.setText(mStudentDao.SQLite_query(name));
                    Toast.makeText(this,"信息查询成功",Toast.LENGTH_SHORT).show();
                }

            }
        }
    }

}
