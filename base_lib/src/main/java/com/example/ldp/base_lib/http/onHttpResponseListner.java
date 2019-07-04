package com.example.ldp.base_lib.http;

/**
 * created by Da Peng at 2019/6/21
 */
public interface onHttpResponseListner {

    void onSuccess(Object object);

    void onFailed(Exception e);

}
