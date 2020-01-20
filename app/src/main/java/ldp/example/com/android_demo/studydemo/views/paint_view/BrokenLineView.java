package ldp.example.com.android_demo.studydemo.views.paint_view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ldp at 2018/12/18
 */
public class BrokenLineView extends View {

    private Paint mPaint;
    private int width;
    private int height;
    private List<Integer> mData;
    private Paint mPaint1;
    private int marginLeft;
    private Paint mPaint2;
    private Paint mPaint3;
    private Path mPathLine;
    private Path mPathOther;

    public BrokenLineView(Context context) {
        this(context, null);
    }

    public BrokenLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BrokenLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        // barMargin = (width - paddingLeft -paddingRight)/(count/2-1);
        marginLeft = paddingLeft;
        mPathLine = new Path();
        mPathOther = new Path();
        for (int i = 0; i < mData.size(); i = i + 2) {
            if (i == mData.size() - 2) {
                break;
            } else {
                float startX = marginLeft + mData.get(i) * width / 100;
                float startY = height - mData.get(i + 1) * height / 100;
                float stopX = marginLeft + mData.get(i + 2) * width / 100;
                float stopY = height - mData.get(i + 3) * height / 100;
                
                mPathLine.moveTo(startX,startY);
                mPathLine.lineTo(stopX,stopY);
                if (i==0){
                    mPathOther.moveTo(startX,startY);
                }
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "pointX", "pointY", mPathLine)
                    .setDuration(20_000);
            //objectAnimator.setInterpolator(new OvershootInterpolator());
            objectAnimator.start();
        }

    }

    PointF mPoint=new PointF();

    public void setPointX(float x){
     //   Log.e("Tag","x="+x);
        mPoint.x=  x;
        //mPathOther.lineTo(mPoint.x,mPoint.y);
        //invalidate();
    }

    public void setPointY(float y){
      //  Log.e("Tag","y="+y);
        mPoint.y=  y;
        mPathOther.lineTo(mPoint.x,mPoint.y);
        invalidate();
    }

        @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, 0, 0, height, mPaint1);
        canvas.drawLine(0, height, width, height, mPaint1);
        for (int i = 0; i < mData.size(); i = i + 2) {
            if (i == mData.size() - 2) {
                break;
            } else {
                float startX = marginLeft + mData.get(i) * width / 100;
                float startY = height - mData.get(i + 1) * height / 100;
                float stopX = marginLeft + mData.get(i + 2) * width / 100;
                float stopY = height - mData.get(i + 3) * height / 100;
                         ////canvas.drawLine(startX, startY, stopX, stopY, mPaint);
                //偏移
                //float offsetX = startX+(stopX-startX)*progress/PROGRESS_MAX;
                //float offsetY = startY+(stopY-startY)*progress/PROGRESS_MAX;

                //canvas.drawLine(startX, startY, offsetX, offsetY, mPaint);

                canvas.drawCircle(startX, startY, 5, mPaint2);
                canvas.drawCircle(stopX, stopY, 5, mPaint2);

                canvas.drawLine(startX, 0, startX, height, mPaint3);
                canvas.drawLine(stopX, 0, stopX, height, mPaint3);

            }

            canvas.drawPath(mPathOther,mPaint);

//            if (progress<PROGRESS_MAX){
//                invalidate();
//            }
//            progress+=1;
        }

    }

    private void setData() {
        mData = new ArrayList<>();
        mData.add(10);
        mData.add(10);
        mData.add(20);
        mData.add(5);
        mData.add(30);
        mData.add(30);
        mData.add(40);
        mData.add(50);
        mData.add(50);
        mData.add(90);
        mData.add(60);
        mData.add(50);
        mData.add(70);
        mData.add(30);
        mData.add(80);
        mData.add(40);
        //        mFloats = new float[]{10,10,20,5,30,30,40,50,50,90,60,50,70,30};
    }
    private void init() {
        setData();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint);

        mPaint1 = new Paint();
        mPaint1.setAntiAlias(true);
        mPaint1.setColor(Color.BLACK);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setStrokeWidth(5);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLACK);
        mPaint2.setAntiAlias(true);
        mPaint2.setStyle(Paint.Style.FILL);

        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{20, 10}, 10);
        mPaint3 = new Paint();
        mPaint3.setPathEffect(dashPathEffect);
        mPaint3.setAntiAlias(true);
        setLayerType(LAYER_TYPE_SOFTWARE, mPaint3);
    }
}
