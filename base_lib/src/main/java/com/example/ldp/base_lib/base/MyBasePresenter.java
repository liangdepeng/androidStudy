package com.example.ldp.base_lib.base;

/**
 * created by Da Peng at 2019/6/21
 */
public abstract class MyBasePresenter <V extends MyBaseView> implements BasePresenter {

    protected V mView = null;

    MyBasePresenter(V mView) {
        this.mView = mView;
    }

    @Override
    public void onDetachView() {
        mView = null;
    }
}
