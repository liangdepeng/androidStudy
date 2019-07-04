package ldp.example.com.android_demo.mvp.base;

import android.widget.Toast;

import ldp.example.com.android_demo.MyApplication;

/**
 * created by Da Peng at 2019/6/20
 */
public abstract class BasePresenterImpl<V extends IBaseView> implements BasePresenter {

    protected V mView = null;

    public BasePresenterImpl(V mView) {
        this.mView = mView;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }

}
