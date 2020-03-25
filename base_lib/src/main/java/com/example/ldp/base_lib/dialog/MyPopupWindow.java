package com.example.ldp.base_lib.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.example.ldp.base_lib.utils.AppUtils;

/**
 * created by Da Peng at 2019/11/4
 */

public class MyPopupWindow extends PopupWindow {

    private View view, popupView;
    private float bgLevel;
    private Context mContext;
    private int layoutResId, width, height, animationStyle;
    private int upAnimationStyle, downAnimationStyle;
    private boolean isShowBg, isOutSideCancel;
    private PopupViewInterface listener;
    private int rootGravity = Gravity.TOP | Gravity.START;       // 以左上角为计算参照点

    private MyPopupWindow(Builder builder) {
        this.mContext = builder.context;
        this.view = builder.view;
        this.bgLevel = builder.bgLevel;
        this.layoutResId = builder.layoutResId;
        this.width = builder.width;
        this.height = builder.height;
        this.animationStyle = builder.animationStyle;
        this.upAnimationStyle = builder.upAnimationStyle;
        this.downAnimationStyle = builder.downAnimationStyle;
        this.isShowBg = builder.isShowBg;
        this.isOutSideCancel = builder.isOutSideCancel;
        this.listener = builder.listener;
        initView();
    }

    private void initView() {
        if (layoutResId != 0) {
            popupView = LayoutInflater.from(mContext).inflate(layoutResId, null, false);
        } else if (view != null) {
            popupView = view;
        }

        if (listener != null) {
            listener.getPopupContentView(popupView);
        }

        setContentView(popupView);
        setWidthAndHeight(width, height);
        //设置透明背景
        setBackgroundDrawable(new ColorDrawable(0x00000000));
        //设置outside可点击
        setOutsideTouchable(isOutSideCancel);
        setFocusable(isOutSideCancel);

        // { PopupWindow.ANIMATION_STYLE_DEFAULT = -1}
        setAnimationStyle(animationStyle == 0 ? -1 : animationStyle);

    }

    private void setBackGroundLevel(boolean isShowBg, float bgLevel) {
        if (isShowBg) {
            Window window = ((Activity) mContext).getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.alpha = bgLevel == 0 ? 0.8f : bgLevel;
            window.setAttributes(params);
        }
    }

    private void setWidthAndHeight(int width, int height) {
        if (width == 0 || height == 0) {
            setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            setWidth(width);
            setHeight(height);
        }
    }

    public interface PopupViewInterface {
        void getPopupContentView(View popupView);
    }

    /**
     * 根据位置自适应向上还是向下弹出
     *
     * @param anchorView 目标View
     * @param xoff       x方向偏移
     * @param yoff       y方向偏移
     */
    public void showVerticalAutomatic(View anchorView, int xoff, int yoff) {
        int[] offsets = calculateVerticalPopWindowPos(anchorView, popupView, 0, true);
        showAtLocation(anchorView, rootGravity, offsets[0] + xoff, offsets[1] + yoff);
    }

    /**
     * 根据位置自适应向左还是向右弹出
     *
     * @param anchorView 目标View
     * @param xoff       x方向偏移
     * @param yoff       y方向偏移
     */
    public void showHorizontalAutomatic(View anchorView, int xoff, int yoff) {
        int[] offsets = calculateHorizontalPopupWindow(anchorView, popupView, 0, true);
        showAtLocation(anchorView, rootGravity, offsets[0] + xoff, offsets[1] + yoff);
    }

    /**
     * 向左弹出
     *
     * @param anchorView
     * @param xoff
     * @param yoff
     */
    public void showAnchorViewLeft(View anchorView, int xoff, int yoff) {
        int[] offsets = calculateHorizontalPopupWindow(anchorView, popupView, Gravity.START, false);
        // 以左上角为计算参照点  Gravity.TOP | Gravity.START
        showAtLocation(anchorView, rootGravity, offsets[0] + xoff, offsets[1] + yoff);
    }

    /**
     * 向右弹出
     *
     * @param anchorView
     * @param xoff
     * @param yoff
     */
    public void showAnchorViewRight(View anchorView, int xoff, int yoff) {
        int[] offsets = calculateHorizontalPopupWindow(anchorView, popupView, Gravity.END, false);
        showAtLocation(anchorView, rootGravity, offsets[0] + xoff, offsets[1] + yoff);
    }

    /**
     * 向上弹出
     *
     * @param anchorView
     * @param xoff
     * @param yoff
     */
    public void showAnchorViewTop(View anchorView, int xoff, int yoff) {
        int[] offsets = calculateVerticalPopWindowPos(anchorView, popupView, Gravity.TOP, false);
        showAtLocation(anchorView, rootGravity, offsets[0] + xoff, offsets[1] + yoff);
    }

    /**
     * 向下弹出
     *
     * @param anchorView
     * @param xoff
     * @param yoff
     */
    public void showAnchorViewBottom(View anchorView, int xoff, int yoff) {
        int[] offsets = calculateVerticalPopWindowPos(anchorView, popupView, Gravity.BOTTOM, false);
        showAtLocation(anchorView, rootGravity, offsets[0] + xoff, offsets[1] + yoff);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        setBackGroundLevel(isShowBg, 1.0f);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        setBackGroundLevel(isShowBg, bgLevel);
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        setBackGroundLevel(isShowBg, bgLevel);
        super.showAtLocation(parent, gravity, x, y);
    }

    /**
     * @param anchorView       锚点View
     * @param popupContentView popupView
     * @param gravity          显示在左边或者右边
     * @param showAutomatic    是否自适应 根据锚点View位置 左右显示
     * @return
     */
    private int[] calculateHorizontalPopupWindow(View anchorView, View popupContentView, int gravity, boolean showAutomatic) {
        int[] popupWindowLoc = new int[2];
        int[] anchorLoc = new int[2];

        anchorView.getLocationOnScreen(anchorLoc);
        int anchorViewHeight = anchorView.getHeight();
        int anchorViewWidth = anchorView.getWidth();

        // 获取屏幕的高宽
        final int screenHeight = AppUtils.getScreenHeight(mContext);
        final int screenWidth = AppUtils.getScreenWidth(mContext);

        popupContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算popupView的高宽
        final int popupViewHeight = popupContentView.getMeasuredHeight();
        final int popupViewWidth = popupContentView.getMeasuredWidth();

        if (showAutomatic) {
            boolean isNeedShowLeft = screenWidth - anchorLoc[0] - anchorViewWidth < popupViewWidth;
            if (isNeedShowLeft) {
                popupWindowLoc[0] = anchorLoc[0] - popupViewWidth;
                popupWindowLoc[1] = anchorViewHeight / 2 + anchorLoc[1] - popupViewHeight / 2;
                //setAnimationStyle(R.style.popup_anim_left);
            } else {
                popupWindowLoc[0] = anchorLoc[0] + anchorViewWidth;
                popupWindowLoc[1] = anchorViewHeight / 2 + anchorLoc[1] - popupViewHeight / 2;
                //setAnimationStyle(R.style.popup_anim_left);
            }
        } else {
            if (gravity == Gravity.START) {
                popupWindowLoc[0] = anchorLoc[0] - popupViewWidth;
                popupWindowLoc[1] = anchorViewHeight / 2 + anchorLoc[1] - popupViewHeight / 2;
                //setAnimationStyle(R.style.popup_anim_left);
            } else if (gravity == Gravity.END) {
                popupWindowLoc[0] = anchorLoc[0] + anchorViewWidth;
                popupWindowLoc[1] = anchorViewHeight / 2 + anchorLoc[1] - popupViewHeight / 2;
               // setAnimationStyle(R.style.popup_anim_left);
            }
        }

        return popupWindowLoc;
    }

    /**
     * 计算竖直方向偏移
     */
    private int[] calculateVerticalPopWindowPos(final View anchorView, final View popupContentView, int gravity, boolean showAutomatic) {
        final int[] popupWindowLoc = new int[2];
        final int[] anchorLoc = new int[2];

        // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = AppUtils.getScreenHeight(mContext);
        final int screenWidth = AppUtils.getScreenWidth(mContext);

        popupContentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算popupView的高宽
        final int popupViewHeight = popupContentView.getMeasuredHeight();
        final int popupViewWidth = popupContentView.getMeasuredWidth();

        Log.e("MyPopup", "screenHeight: " + screenHeight +
                "   anchorView Y方向： " + anchorLoc[1] +
                "   anchorView Height： " + anchorHeight +
                "   anchorView 下方剩余高度：  " + (screenHeight - anchorLoc[1] - anchorHeight) +
                "   popupViewHeight：" + popupViewHeight);

        if (showAutomatic) {
            // 判断需要向上弹出还是向下弹出显示
            final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < popupViewHeight);
            if (isNeedShowUp) {
                //popupWindowLoc[0] = screenWidth - popupViewWidth;
                popupWindowLoc[0] = (screenWidth - popupViewWidth) / 2;
                popupWindowLoc[1] = anchorLoc[1] - popupViewHeight;
                if (upAnimationStyle != 0) {
                    setAnimationStyle(upAnimationStyle);
                }
            } else {
                //popupWindowLoc[0] = screenWidth - popupViewWidth;
                popupWindowLoc[0] = (screenWidth - popupViewWidth) / 2;
                popupWindowLoc[1] = anchorLoc[1] + anchorHeight;
                if (downAnimationStyle != 0) {
                    setAnimationStyle(downAnimationStyle);
                }
            }
        } else {

            if (gravity == Gravity.TOP) {
                //popupWindowLoc[0] = screenWidth - popupViewWidth;
                popupWindowLoc[0] = (screenWidth - popupViewWidth) / 2;
                popupWindowLoc[1] = anchorLoc[1] - popupViewHeight;
                if (upAnimationStyle != 0) {
                    setAnimationStyle(upAnimationStyle);
                }
            } else if (gravity == Gravity.BOTTOM) {
                //popupWindowLoc[0] = screenWidth - popupViewWidth;
                popupWindowLoc[0] = (screenWidth - popupViewWidth) / 2;
                popupWindowLoc[1] = anchorLoc[1] + anchorHeight;
                if (downAnimationStyle != 0) {
                    setAnimationStyle(downAnimationStyle);
                }
            }

        }


        return popupWindowLoc;
    }

    public static class Builder {

        public View view;
        public Context context;
        public float bgLevel;
        public PopupViewInterface listener;
        public boolean isShowBg, isOutSideCancel;
        public int layoutResId, width, height, animationStyle, upAnimationStyle, downAnimationStyle;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setView(int layoutResId) {
            this.view = null;
            this.layoutResId = layoutResId;
            return this;
        }

        public Builder setView(View view) {
            this.view = view;
            this.layoutResId = 0;
            return this;
        }

        public Builder setContentViewClickListener(PopupViewInterface listener) {
            this.listener = listener;
            return this;
        }

        public Builder setParams(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setBackGroundLevel(float level) {
            this.bgLevel = level;
            return this;
        }

        public Builder setIsShowBg(boolean isShowBg) {
            this.isShowBg = isShowBg;
            return this;
        }

        public Builder setOutSideCancel(boolean isOutSideCancel) {
            this.isOutSideCancel = isOutSideCancel;
            return this;
        }

        public Builder setAnimationStyle(int animationStyle) {
            this.animationStyle = animationStyle;
            return this;
        }

        public Builder setUpShowAnimationStyle(int upAnimationStyle) {
            this.upAnimationStyle = upAnimationStyle;
            return this;
        }

        public Builder setDownShowAnimationStyle(int downAnimationStyle) {
            this.downAnimationStyle = downAnimationStyle;
            return this;
        }

        public MyPopupWindow build() {
            return new MyPopupWindow(this);
        }

    }

}

