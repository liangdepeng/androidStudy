package com.example.ldp.base_lib.base;

/**
 * created by Da Peng at 2019/6/21
 */
public interface MyBaseView {

    void showProgressDialog();

    void hideProgressDialog();

    void showLongToast(String message);

    void showShortToast(String message);

    void startRequestInfo();

}
