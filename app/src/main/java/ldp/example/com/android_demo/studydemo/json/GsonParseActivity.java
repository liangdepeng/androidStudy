package ldp.example.com.android_demo.studydemo.json;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ldp.base_lib.bean.HtmlInfoBean;
import com.example.ldp.base_lib.http.HttpRequestInfo;
import com.example.ldp.base_lib.http.onHttpResponseListner;
import com.example.ldp.base_lib.utils.LogUtils;

import ldp.example.com.android_demo.R;

/**
 * @author mini
 */
public class GsonParseActivity extends AppCompatActivity implements View.OnClickListener, onHttpResponseListner {

    private EditText gson_et;
    private Button gson_btn;
    private TextView gson_txt;
    private HttpRequestInfo mHttpRequestInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gson_parse);
        mHttpRequestInfo = new HttpRequestInfo(this);
        initView();
    }

    private void initView() {
        gson_et = (EditText) findViewById(R.id.gson_et);
        gson_btn = (Button) findViewById(R.id.gson_btn);
        gson_txt = (TextView) findViewById(R.id.gson_txt);

        gson_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gson_btn:
                submit();
                break;
            default:
                break;
        }
    }

    private void submit() {
        // validate
        String et = gson_et.getText().toString().trim();
        if (TextUtils.isEmpty(et)) {
            gson_et.setError(" 内容不能为空 ");
            Toast.makeText(this, "et不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mHttpRequestInfo.testAsyncTaskHttpRequest(et, HtmlInfoBean.class);
        }

        // TODO validate success, do something


    }

    @Override
    public void onSuccess(Object object) {
        LogUtils.d("gson", object.toString());
        HtmlInfoBean htmlInfoBean = (HtmlInfoBean) object;
        gson_txt.setText(htmlInfoBean.toString());
    }

    @Override
    public void onFailed(Exception e) {

    }
}
