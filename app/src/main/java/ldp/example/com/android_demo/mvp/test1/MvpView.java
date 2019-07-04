package ldp.example.com.android_demo.mvp.test1;

/**
 * created by Da Peng at 2019/6/20
 */
public interface MvpView {

    void showProgressDialog();

    void hideProgressDialog();

    void setUserNameError();

    void setUserMmError();

    void navigateToHome();

}
