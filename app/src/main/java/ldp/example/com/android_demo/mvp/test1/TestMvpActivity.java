package ldp.example.com.android_demo.mvp.test1;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ldp.example.com.android_demo.R;

public class TestMvpActivity extends AppCompatActivity implements MvpView {

    private ProgressDialog mProgressDialog;
    private MyPresenter mPresenter;
    private EditText mName;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_mvp);
        mPresenter = new MyPresenter(this, new MyContacts());
        initView();
    }

    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("加载中...");
        //mProgressDialog.setCustomTitle(new View(this));
        mName = (EditText) findViewById(R.id.name);
        mPassword = (EditText) findViewById(R.id.password);
        findViewById(R.id.button_mvp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.checkInfo(mName.getText().toString().trim(), mPassword.getText().toString().trim());
            }
        });
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.cancel();
    }

    @Override
    public void setUserNameError() {
        mPassword.setError(getString(R.string.username_error));
    }

    @Override
    public void setUserMmError() {
        mName.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetachView();
        super.onDestroy();
    }
}
