package ldp.example.com.android_demo.mvp.base.test.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ldp.example.com.android_demo.MainActivity;
import ldp.example.com.android_demo.R;
import ldp.example.com.android_demo.mvp.base.BaseMvpActivity;
import ldp.example.com.android_demo.mvp.base.test.callback.TestMvpIView;
import ldp.example.com.android_demo.mvp.base.test.presenter.TestMvpPresenter;

import com.example.ldp.base_lib.utils.AppUtils;

public class TestMvpAvtivity2 extends BaseMvpActivity implements TestMvpIView, View.OnClickListener {

    private TestMvpPresenter mPresenter;
    private EditText mName;
    private EditText mPassword;
    private Button mSure_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initPresenter() {
        mPresenter = new TestMvpPresenter(this);
    }

    @Override
    protected void initData() {
        mPresenter.requestWeatherInfo();
    }

    @Override
    protected void initView() {
        mName = (EditText) findViewById(R.id.name_et);
        mPassword = (EditText) findViewById(R.id.password_et);
        mSure_btn = (Button) findViewById(R.id.sure_btn);
        mSure_btn.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_test_mvp_avtivity2;
    }

    @Override
    public void showWeatherInfo() {

    }

    @Override
    public void showLoginError() {
        showToast(getString(R.string.user_login_error));
    }

    @Override
    public void showLoginSuccess() {
        showToast(getString(R.string.user_login_success));
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.sure_btn) {
            mPresenter.checkInfo(AppUtils.stringRemoveSpace(mName.getText()), AppUtils.stringRemoveSpace(mPassword.getText()));
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDetachView();
        super.onDestroy();
    }
}
