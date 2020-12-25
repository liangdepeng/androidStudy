package com.example.ldp.base_lib.base;

/**
 * created by Da Peng at 2019/6/21
 */
public abstract class BasePresenter<V extends IBaseView> implements IBasePresenter {

    protected V mView = null;

    BasePresenter(V mView) {
        this.mView = mView;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }
}
