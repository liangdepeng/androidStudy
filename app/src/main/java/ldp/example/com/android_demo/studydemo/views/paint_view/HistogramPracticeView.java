package ldp.example.com.android_demo.studydemo.views.paint_view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ldp at 2018/12/19
 */
public class HistogramPracticeView extends View {

    private Paint mPaint;
    private Paint mPaint1;
    private int width;
    private int height;
    private float progress;
    private int barHeight;
    private int count = 6;
    private int barMargin = 30;
    private List<Integer> mData;
    private int MAX_PROGRESS = 100;

    public HistogramPracticeView(Context context) {
        this(context, null);
    }

    public HistogramPracticeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramPracticeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setData();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLACK);
        mPaint1.setPathEffect(new DashPathEffect(new float[]{20,10},20));
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.STROKE);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = h;
        int paddingBottom = getPaddingBottom();
        int paddingTop = getPaddingTop();

        barHeight = (height - paddingBottom - paddingTop - barMargin * (count + 1)) / count;
        setAnimator();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int length = width / 5;
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(length * i, 0, length * i, height, mPaint1);
        }

        for (int i = 0; i < mData.size(); i++) {
            float left = 0;
            float top = barMargin * (i + 1) + barHeight * i;
            float right = left + progress / MAX_PROGRESS * width * mData.get(i) / 100;
            float bottom = top + barHeight;
            canvas.drawRect(left, top, right, bottom, mPaint);

        }

        if (progress < MAX_PROGRESS) {
            invalidate();
        }

    }

    private void setData() {
        mData = new ArrayList<>();
        mData.add(100);
        mData.add(50);
        mData.add(80);
        mData.add(60);
        mData.add(30);
        mData.add(70);
    }

    private void setAnimator(){
        ObjectAnimator progress = ObjectAnimator.ofFloat(this, "progress", 0, 100);
        progress.setDuration(10000);
        progress.start();
    }

    public void setProgress(float progress) {
        this.progress =progress;
    }
}
