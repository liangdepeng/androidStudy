package com.example.ldp.base_lib.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.ldp.base_lib.R;


/**
 * @author da peng
 * @date 2019/11/4
 */
public class PopupWindowHelper {

    // 上下自适应
    public static void showVerticalAutomaticPopupView(Context context, View anchorView, int layoutResId, int xoff, int yoff) {
        MyPopupWindow popupView = new MyPopupWindow.Builder(context)
                .setIsShowBg(true)
                .setOutSideCancel(true)
                .setView(layoutResId)
                .build();
        popupView.showVerticalAutomatic(anchorView, xoff, yoff);
    }

    public static void showVerticalAutomaticPopupView(Context context, View anchorView, View view, int xoff, int yoff) {
        MyPopupWindow popupView = new MyPopupWindow.Builder(context)
                .setIsShowBg(true)
                .setOutSideCancel(true)
                .setView(view)
                .build();
        popupView.showVerticalAutomatic(anchorView, xoff, yoff);
    }

    // 左右自适应
    public static void showHorizontalAutomaticPopupView(Context context, View anchorView, int layoutResId, int xoff, int yoff) {
        MyPopupWindow popupView = new MyPopupWindow.Builder(context)
                .setIsShowBg(true)
                .setOutSideCancel(true)
                .setView(layoutResId)
                .build();
        popupView.showHorizontalAutomatic(anchorView, xoff, yoff);
    }

    public static void showHorizontalAutomaticPopupView(Context context, View anchorView, View view, int xoff, int yoff) {
        MyPopupWindow popupView = new MyPopupWindow.Builder(context)
                .setIsShowBg(true)
                .setOutSideCancel(true)
                .setView(view)
                .build();
        popupView.showHorizontalAutomatic(anchorView, xoff, yoff);
    }


    // 底部弹出 layoutResId
    public static void bottomPopupView(Context context, View anchorView, int layoutResId, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(layoutResId)
                .setParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setAnimationStyle(R.style.popup_anim_bottom)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
    }

    // 底部弹出 contentView
    public static void bottomPopupView(Context context, View anchorView, View contentView, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(contentView)
                .setParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setAnimationStyle(R.style.popup_anim_bottom)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAtLocation(anchorView, Gravity.BOTTOM, 0, 0);
    }

    // 左边弹出 layoutResId
    public static void showLeftPopupView(Context context, View anchorView, int layoutResId, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(layoutResId)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAnchorViewLeft(anchorView, 0, 0);
    }

    // 左边弹出 contentView
    public static void showLeftPopupView(Context context, View anchorView, View contentView, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(contentView)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAnchorViewLeft(anchorView, 0, 0);
    }


    public static void showRightPopupView(Context context, View anchorView, int layoutResId, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(layoutResId)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAnchorViewRight(anchorView, 0, 0);
    }

    public static void showBottomPopupView(Context context, View anchorView, int layoutResId, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(layoutResId)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAnchorViewBottom(anchorView, 0, 0);
    }

    public static void showTopPopupView(Context context, View anchorView, int layoutResId, MyPopupWindow.PopupViewInterface listener) {
        MyPopupWindow popupWindow = new MyPopupWindow.Builder(context).setView(layoutResId)
                .setOutSideCancel(true)
                .setIsShowBg(true)
                .setContentViewClickListener(listener)
                .build();
        popupWindow.showAnchorViewTop(anchorView, 0, 0);
    }
}
