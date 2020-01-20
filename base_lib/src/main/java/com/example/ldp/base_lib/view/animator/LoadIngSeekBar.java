package com.example.ldp.base_lib.view.animator;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

/**
 * created by dp at 2018/12/19
 */
public class LoadIngSeekBar extends View {

    private int width;
    private int height;
    private float progress;
    private int MAX_PROGRESS = 100;
    private List<String> mData;
    private float mRadius;
    private RectF mRectF;
    private Paint mPaintArc;
    private Paint mPaintText;

    public LoadIngSeekBar(Context context) {
        this(context, null);
    }

    public LoadIngSeekBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadIngSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setData();

        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStrokeWidth(30);
        mPaintText.setStyle(Paint.Style.STROKE);
        mPaintText.setColor(Color.BLUE);
        mPaintText.setStrokeCap(Paint.Cap.ROUND);

        mPaintArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintArc.setColor(Color.BLACK);
        mPaintArc.setTextSize(80);
        mPaintArc.setFakeBoldText(true);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int a = (width - paddingLeft - paddingRight) / 2;
        int b = (height - paddingTop - paddingBottom) / 2;
        mRadius = (float) (Math.min(a, b) * 0.8);
        mRectF = new RectF(paddingLeft * 3, paddingTop * 3, mRadius * 2, mRadius * 2);
        setAnimator();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mRectF, 0, progress / MAX_PROGRESS * 360, false, mPaintText);

        float textWidth = mPaintArc.measureText(mData.get((int) progress));
        float baseLineY = Math.abs(mPaintArc.ascent() + mPaintArc.descent()) / 2;

        canvas.drawText(mData.get((int) progress), mRadius - textWidth / 2, mRadius + baseLineY, mPaintArc);

        if (progress < MAX_PROGRESS) {
            invalidate();
        }

    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnimator() {
        ObjectAnimator progress = ObjectAnimator.ofFloat(this, "progress", 0, MAX_PROGRESS);
        progress.setDuration(8000);
        progress.start();
    }

    public void setData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 101; i++)
            mData.add(String.valueOf(i) + "%");
    }
}

//    public Path getMyPath() {
//        Path path = new Path();
//        path.moveTo(width / 2, height / 2);
//        RectF rectF = new RectF(0, 0, mRadius * 2, mRadius * 2);
//        path.addArc(rectF, 0, 360);
//        //path.addCircle(width/2,height/2,mRadius,Path.Direction.CW);
//        return path;
//    }