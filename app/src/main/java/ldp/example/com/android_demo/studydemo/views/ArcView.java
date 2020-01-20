package ldp.example.com.android_demo.studydemo.views;

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

/**
 * created by ldp at 2018/12/17
 */
public class ArcView extends View {

    private int width;
    private int height;
    private Paint mPaint;
    private Paint mPaint1;
    private Paint mPaint2;
    private int mRadius;
    private int progress;
    private int MAX_PROGRESS = 100;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int a = (width - paddingLeft - paddingRight) / 2;
        int b = (height - paddingTop - paddingBottom) / 2;
        mRadius = Math.min(a, b);

    }



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawArc(new RectF(0f, 0f, mRadius * 2f, mRadius * 2f) , 0f, 120 * progress / 100,true, mPaint);
        canvas.drawArc(0, 0, mRadius * 2, mRadius * 2, 120, 120 * progress / 100, true, mPaint1);
        canvas.drawArc(0, 0, mRadius * 2, mRadius * 2, 240, 120 * progress / 100, true, mPaint2);

        if (progress < MAX_PROGRESS) {
            invalidate();
        }
        progress += 1;
    }


    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.RED);
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint1);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.GREEN);
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.FILL);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint2);
    }
}
