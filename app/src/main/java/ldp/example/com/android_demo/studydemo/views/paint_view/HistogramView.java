package ldp.example.com.android_demo.studydemo.views.paint_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ldp at 2018/12/17
 */
public class HistogramView extends View {

    private int width;
    private int height;
    private List<Integer> mData;
    private Paint mPaint;
    private Paint mPaint1;
    private int count = 5;
    private int barMargin=30;
    private int barWidth;
    private float progress;
    private float MAX_PROGRESS = 100;

    public HistogramView(Context context) {
        this(context, null);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setData();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);

        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10}, 10);
        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(Color.BLACK);
        mPaint1.setPathEffect(dashPathEffect);
        setLayerType(LAYER_TYPE_SOFTWARE,mPaint1);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int HeightMode = MeasureSpec.getMode(heightMeasureSpec);
        int HeightSize = MeasureSpec.getSize(heightMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.EXACTLY:

                break;
            case MeasureSpec.AT_MOST:

                break;
            case MeasureSpec.UNSPECIFIED:

                break;
        }
        Log.d("widthMode", "widthMode" + widthMode + " " + MeasureSpec.EXACTLY);
        Log.d("widthSize", "widthSize" + widthSize);
        Log.d("HeightMode", "HeightMode" + HeightMode);
        Log.d("HeightSize", "HeightSize" + HeightSize);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        barWidth = (width - paddingLeft - paddingRight - barMargin * (count + 1)) / count;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float min_height = height / 5;
        for (int i = 0; i < 5; i++) {
            canvas.drawLine(10, min_height * i, width, min_height * i, mPaint1);
        }

        for (int i = 0; i < mData.size(); i++) {
            float left = barMargin*(i+1)+barWidth*i;
            float radio = progress / MAX_PROGRESS;
            float top = height - radio *height * mData.get(i) / 100;
          //  Log.e("radio","radio = "+radio+" top="+top);
            canvas.drawRect(left, top,left+barWidth,height,mPaint);

        }
        if (progress<MAX_PROGRESS) {
            invalidate();
        }
        progress+=1;

    }

    private void setData() {
        mData = new ArrayList<>();
        mData.add(10);
        mData.add(50);
        mData.add(30);
        mData.add(20);
        mData.add(60);
    }

}
