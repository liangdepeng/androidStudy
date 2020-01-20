package com.example.ldp.base_lib.pseronalview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.ldp.base_lib.R;
import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;

/**
 * created by Da Peng at 2019/10/14
 * <p>
 * 随手点击拖动移动的View
 */
public class BallView extends View {

    private Paint mPaint;
    private float moveX = 100;
    private float moveY = 100;
    private float screenY;
    private float screenX;
    private float radius;
    private int mColor;
    private Context mContext;

    public BallView(Context context) {
        this(context, null);
    }

    public BallView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BallView);
        if (typedArray != null) {
            mColor = typedArray.getColor(R.styleable.BallView_viewColor, Color.RED);
            radius = typedArray.getFloat(R.styleable.BallView_radius, 66);
            typedArray.recycle();
        }
        initPaint(context);
        mContext = context;
    }

    private void initPaint(Context context) {
        screenX = AppUtils.getScreenWidth(context);
        screenY = AppUtils.getScreenHeight(context);

        mPaint = new Paint();
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
    }

    public void setLocation(String w, float x, float y, float radius) {
        this.moveX = x;
        this.moveY = y;
        this.radius = radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        {
            LogUtils.e("onDrawBefore", moveX + "  " + moveY);

            // 防止view 越界
            moveX = moveX + radius > screenX ? screenX - radius : moveX;
            moveX = moveX - radius < 0 ? radius : moveX;

            moveY = moveY + radius > screenY ? screenY - radius : moveY;
            moveY = moveY - radius < 0 ? radius : moveY;

            LogUtils.e("onDrawAfter", moveX + "  " + moveY);

            canvas.drawCircle(moveX, moveY, radius, mPaint);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveX = event.getX();
        moveY = event.getY();

        invalidate();
        return true;
        //        return super.onTouchEvent(event);
    }


    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.w("onMeasure", widthMeasureSpec + "  " + heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    // 获取状态栏高度
    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }

    // 获取导航栏高度
    public int getNavigationBarHeight() {
        int rid = getResources().getIdentifier("config_showNavigationBar", "bool", "android");
        if (rid != 0) {
            int resourceId = mContext.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
            return mContext.getResources().getDimensionPixelSize(resourceId);
        } else {
            return 0;
        }


    }

}
