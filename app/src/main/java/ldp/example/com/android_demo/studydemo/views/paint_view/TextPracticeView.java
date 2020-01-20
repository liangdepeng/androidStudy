package ldp.example.com.android_demo.studydemo.views.paint_view;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * created by ldp at 2018/12/19
 */
public class TextPracticeView extends View {

    private Paint mPaint;
    private int v_width;
    private int v_height;
    private Context context;
    private int mWidth;
    private int mHeight;

    public TextPracticeView(Context context) {
        this(context, null);
        this.context = context;
    }

    public TextPracticeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextPracticeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        getScreen();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setTextSize(40);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("xy",widthMeasureSpec + " " + heightMeasureSpec);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        v_width = w;
        v_height = h;
        Log.e("xy2",w + " " + h);

        //path.lineTo();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setAnimator() {
        Path path = new Path();
        path.moveTo(getX(),getY());
        path.lineTo(0,200);
        path.lineTo(200,0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "pointX", "pointY", path);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(this, "scaleX", 1, 2, 3, 2, 1);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(this, "scaleY", 1, 2, 3, 2, 1);
        ObjectAnimator rotation = ObjectAnimator.ofFloat(this, "rotation", 0, 360);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimator,scaleX,scaleY,rotation);
        animatorSet.setDuration(12000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText("Hello",v_width/2,v_height/2,mPaint);
        setAnimator();
    }
    PointF mPoint=new PointF();

    public void setPointX(float pointX){
        mPoint.x = pointX;
    }
    public void setPointY(float pointY){
        mPoint.y = pointY;
    }

    public void getScreen(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
        Log.e("getWH","宽 " + mWidth + " 高 " + mHeight);
    }
}
