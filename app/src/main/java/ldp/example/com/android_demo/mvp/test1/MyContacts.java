package ldp.example.com.android_demo.mvp.test1;

import android.os.Handler;
import android.text.TextUtils;

/**
 * created by Da Peng at 2019/6/20
 */
public class MyContacts {

    public interface onLoginFinishListener {

        void onUsernameError();

        void onPasswordError();

        void onSuccess();
    }

    public void login(final MvpView mvpView, final String name, final String password, final onLoginFinishListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (TextUtils.isEmpty(name)) {
                    listener.onUsernameError();
                }
                if (TextUtils.isEmpty(password)) {
                    listener.onPasswordError();
                }
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){
                    mvpView.showProgressDialog();
                    listener.onSuccess();
                }
            }
        }, 2000);
    }
}
