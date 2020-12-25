package com.example.ldp.base_lib.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


/**
 * @author Da Peng
 */
public abstract class BaseMvpActivity extends BaseActivity{

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        initPresenter();
        initData();
        initView();
    }

    protected abstract void initPresenter();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract int getLayoutResId();

    @Override
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("加载中...");
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 方法1: Activity.getWindow().getDecorView().findViewById(android.R.id.content)
     * 方法2: Activity.findViewById(android.R.id.content)
     * 方法3: Activity.findViewById(android.R.id.content).getRootView()
     *
     * @return contentView
     */
    public View getContentView() {
        return (ViewGroup) getWindow().getDecorView();
    }
}
