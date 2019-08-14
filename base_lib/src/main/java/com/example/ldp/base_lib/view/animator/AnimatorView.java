package com.example.ldp.base_lib.view.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * created by ldp at 2018/12/18
 */
public class AnimatorView extends View {

    private Paint mPaint;
    private int width;
    private int height;

    public AnimatorView(Context context) {
        this(context,null);
    }

    public AnimatorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AnimatorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.YELLOW);
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(width/2,height/2,Math.min(width/2,height/2),mPaint);
    }
}
