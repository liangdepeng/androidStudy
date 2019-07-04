package ldp.example.com.android_demo.mvp.base.test.presenter;

import android.text.TextUtils;

import com.example.ldp.base_lib.http.HttpRequestInfo;
import com.example.ldp.base_lib.http.onHttpResponseListner;

import ldp.example.com.android_demo.mvp.base.BasePresenterImpl;
import ldp.example.com.android_demo.mvp.base.test.callback.TestMvpIView;
import ldp.example.com.android_demo.mvp.base.test.model.InfoBean;

/**
 * created by Da Peng at 2019/6/20
 */
public class TestMvpPresenter extends BasePresenterImpl<TestMvpIView> implements onHttpResponseListner {

    private HttpRequestInfo mHttpRequestInfo = new HttpRequestInfo(this);

    public TestMvpPresenter(TestMvpIView mView) {
        super(mView);
    }

    public void checkInfo(String name, String password) {
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            mView.showLoginError();
        } else {
            mView.showLoginSuccess();
        }
    }

    public void requestWeatherInfo() {
        // 网络请求
        // ......  mHttpRequestInfo.requestInfo(url,InfoBean.class);

        // 模拟网络请求 数据
        InfoBean infoBean = new InfoBean();
        infoBean.setLogin(true);
        infoBean.setInfo("登陆成功");
    }

    @Override
    public void onSuccess(Object object) {

    }

    @Override
    public void onFailed(Exception e) {

    }
}
