package ldp.example.com.android_demo.mvp.base.test.callback;

import ldp.example.com.android_demo.mvp.base.IBaseView;

/**
 * created by Da Peng at 2019/6/20
 */
public interface TestMvpIView extends IBaseView {

    void showWeatherInfo();

    void showLoginError();

    void showLoginSuccess();
}
