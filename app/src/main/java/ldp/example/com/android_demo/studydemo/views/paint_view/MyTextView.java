package ldp.example.com.android_demo.studydemo.views.paint_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * created by ldp at 2018/12/19
 */
public class MyTextView extends View {

    private Paint mPaint;
    private int width;
    private int height;
    private List<String> mList;
    private int mPaddingLeft;
    private int mPaddingTop;
    private int mPaddingRight;
    private int mPaddingBottom;
    private int v_with;
    private int v_height;
    private Paint mPaint1;
    private Paint mPaint2;
    private Paint mPaint3;
    private Paint mPaint4;
    private Paint mPaint5;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public MyTextView(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        mPaddingLeft = getPaddingLeft();
        mPaddingTop = getPaddingTop();
        mPaddingRight = getPaddingRight();
        mPaddingBottom = getPaddingBottom();

        v_with = width - mPaddingLeft - mPaddingRight;
        v_height = height - mPaddingBottom - mPaddingTop;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(mList.get(0), mPaddingLeft, mPaddingTop + 50, mPaint);
        canvas.drawTextOnPath(mList.get(1), getTextPath(), 0, 0, mPaint);
        canvas.drawText(mList.get(2), mPaddingLeft, mPaddingTop + 240, mPaint1);
        canvas.drawText(mList.get(2), mPaddingLeft, mPaddingTop + 300, mPaint2);
        canvas.drawText(mList.get(3), mPaddingLeft, mPaddingTop + 360, mPaint3);
        canvas.drawText(mList.get(4), mPaddingLeft, mPaddingTop + 420, mPaint3);
        canvas.drawRect(mPaddingLeft,mPaddingTop+450,v_with,mPaddingTop+600,mPaint5);
        canvas.drawText(mList.get(5), mPaddingLeft * 3, mPaddingTop + 560, mPaint4);
    }

    public void setListData() {
        mList = new ArrayList<>();
        mList.add("hello , android ");
        mList.add("hello , android hello , android hello , android");
        mList.add("hello , android hello , android");
        mList.add("哈喽 ， 安卓");
        mList.add("Hello android hello , android");
        mList.add("Hello android world");
    }

    public Path getTextPath() {
        Path path = new Path();
        path.moveTo(mPaddingLeft, mPaddingTop + 120);
        path.cubicTo(v_with / 5 * 2 + mPaddingLeft, mPaddingTop + 240, v_with / 5 * 3, mPaddingTop + 60, v_with, mPaddingTop + 240);
        return path;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void init() {
        setListData();

        mPaint = new Paint();
        mPaint.setTextSize(50);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setUnderlineText(true);
        mPaint.setTextSkewX(-0.2f); //文字倾斜度
        //mPaint.setTextAlign(Paint.Align.RIGHT);

        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLUE);
        mPaint1.setTypeface(Typeface.SERIF);
        mPaint1.setAntiAlias(true);
        mPaint1.setDither(true);
        mPaint1.setTextSize(50);
        mPaint1.setFakeBoldText(true);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.BLACK);
        mPaint2.setTypeface(Typeface.SERIF);
        mPaint2.setAntiAlias(true);
        mPaint2.setDither(true);
        mPaint2.setTextSize(50);
        mPaint2.setFakeBoldText(true);  //为粗体
        mPaint2.setStrikeThruText(true); //删除线

        mPaint3 = new Paint();
        mPaint3.setTextSize(50);
        mPaint3.setAntiAlias(true);
        mPaint3.setDither(true);
        mPaint3.setColor(Color.BLACK);
        mPaint3.setTextSkewX(-0.2f);
        //mPaint3.setTextLocale(Locale.JAPANESE);
        // mPaint3.setHinting();
        mPaint3.setSubpixelText(true);
        mPaint3.setShader(new LinearGradient(100, 100, 500, 500, Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP));

        mPaint4 = new Paint();
        mPaint4.setTextSize(100);
        mPaint4.setAntiAlias(true);
        mPaint4.setDither(true);
        mPaint4.setColor(Color.BLACK);
        mPaint4.setShadowLayer(10, 10, 10, Color.GRAY);
        mPaint4.setTextSkewX(-0.2f);
        mPaint4.setFakeBoldText(true);
        //mPaint4.setMaskFilter(new EmbossMaskFilter(new float[]{0,1,1},0.2f,0.2f,0.2f));
        //paint.setMaskFilter(new EmbossMaskFilter(new float[]{0, 1, 1}

        mPaint5 = new Paint();
        mPaint5.setAntiAlias(true);
        mPaint5.setDither(true);
        mPaint5.setColor(Color.RED);
        mPaint5.setStyle(Paint.Style.STROKE);
        mPaint5.setStrokeWidth(3);
    }
}
