package ldp.example.com.android_demo.mvp.test1;

/**
 * created by Da Peng at 2019/6/20
 */
public class MyPresenter implements MyContacts.onLoginFinishListener {

    MvpView mvpView;
    MyContacts myContacts;

    MyPresenter(MvpView mvpView, MyContacts myContacts) {
        this.mvpView = mvpView;
        this.myContacts = myContacts;
    }

    public void checkInfo(String name, String password) {
        if (mvpView != null) {
            myContacts.login(mvpView, name, password, this);
        }
    }

    @Override
    public void onUsernameError() {
        if (mvpView != null) {
            mvpView.setUserNameError();
        }
    }

    @Override
    public void onPasswordError() {
        if (mvpView != null) {
            mvpView.setUserMmError();
        }
    }

    @Override
    public void onSuccess() {
        if (mvpView != null) {
            mvpView.navigateToHome();
            mvpView.hideProgressDialog();
        }
    }

    public void onDetachView() {
        mvpView = null;
    }
}
