package ldp.example.com.android_demo.studydemo.zidingyikj.zdyview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * created by ldp at 2018/8/6
 */
public class circle_view extends View{

    private Paint mPaint;
    private Paint mPaint1;

    public circle_view(Context context) {
        this(context,null);
    }

    public circle_view(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public circle_view(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLUE);
        mPaint1.setAntiAlias(true);
        mPaint1.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int c1 = getWidth()/2;
        int c2 = getHeight()/2;
        canvas.drawCircle(c1,c2,200,mPaint);
        canvas.drawCircle(c1,c2,150,mPaint1);

        super.onDraw(canvas);
    }
}
