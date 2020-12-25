package ldp.example.com.android_demo.studydemo.contentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ldp.example.com.android_demo.R;
import com.example.ldp.base_lib.base.BasePermissionActivity;

public class ContentProviderPermissionActivity extends BasePermissionActivity implements View.OnClickListener {

    private EditText etCname;
    private EditText etCnumber;
    private EditText etEmail;
    private Button btnBaocun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider);
        findViews();
        getpermission();

    }

    /**
     * Auto-created on 2018-11-02 20:55:36 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        etCname = (EditText) findViewById(R.id.et_cname);
        etCnumber = (EditText) findViewById(R.id.et_cnumber);
        etEmail = (EditText) findViewById(R.id.et_email);
        btnBaocun = (Button) findViewById(R.id.btn_baocun);

        btnBaocun.setOnClickListener(this);
    }

    /**
     * Auto-created on 2018-11-02 20:55:36 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if (v == btnBaocun) {
            //判断是否为空
            String name = etCname.getText().toString().trim();
            String phone = etCnumber.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(ContentProviderPermissionActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            }else{
                // 在 raw_contact表添加联系人
                ContentResolver resolver = this.getContentResolver();
                Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
                Uri datauri = Uri.parse("content://com.android.contacts/data");

                Cursor cursor = resolver.query(uri, null, null, null, null, null);
                int id = cursor.getCount() + 1;

                ContentValues contentValues = new ContentValues();
                contentValues.put("contact_id", id);
                resolver.insert(uri, contentValues);

                //在data表里添加联系人
                ContentValues namevalues = new ContentValues();
                namevalues.put("raw_contact_id", id);
                namevalues.put("data1", name);
                namevalues.put("mimetype", "vnd.android.cursor.item/name");
                resolver.insert(datauri, namevalues);

                ContentValues phonevalues = new ContentValues();
                phonevalues.put("raw_contact_id", id);
                phonevalues.put("data1", phone);
                phonevalues.put("mimetype", "vnd.android.cursor.item/phone_v2");
                resolver.insert(datauri, phonevalues);

                ContentValues emailvalues = new ContentValues();
                emailvalues.put("raw_contact_id", id);
                emailvalues.put("data1", email);
                emailvalues.put("mimetype", "vnd.android.cursor.item/email_v2");
                resolver.insert(datauri, emailvalues);

                Toast.makeText(ContentProviderPermissionActivity.this, "联系人添加完成", Toast.LENGTH_SHORT).show();
            }

        }
    }

    /**
     * 获取读写联系人权限
     */
    private void getpermission() {
        performCodeWithPermission("插入联系人信息", new PermissionCallback() {
            @Override
            public void hasPermission() {

            }

            @Override
            public void noPermission() {
                Toast.makeText(ContentProviderPermissionActivity.this, "没有相关权限", Toast.LENGTH_SHORT).show();
            }
        }, Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_CONTACTS);
    }
}






