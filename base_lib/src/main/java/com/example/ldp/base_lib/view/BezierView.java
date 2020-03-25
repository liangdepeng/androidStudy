package com.example.ldp.base_lib.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.ldp.base_lib.R;
import com.example.ldp.base_lib.utils.AppUtils;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author：Administrator on 2017/3/15 09:18
 * description:文件说明
 * version:版本
 */
public class BezierView extends RelativeLayout {
    private Interpolator[] interpolators ;
    private Drawable drawable[];
    /**
     * 图片的宽高
     */
    private int dWidth = 0 ;
    private int dHeight = 0 ;
    private LayoutParams lp ;
    private Random random ;
    /**
     * 父控件宽高
     */
    private int mWidth = 1080 ;
    private int mHeight = 1920 ;
    private Timer timer = null;
    private TimerTask task = null ;
    private boolean isPlayingAnim = true ;

    public BezierView(Context context) {
        this(context,null);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化数据
     */
    private void init() {
        drawable = new Drawable[5];
        drawable[0] = ContextCompat.getDrawable(getContext(), R.drawable.ic_number_of_buyers);
        drawable[1] = ContextCompat.getDrawable(getContext(),R.drawable.ic_number_of_buyers);
        drawable[2] = ContextCompat.getDrawable(getContext(),R.drawable.ic_number_of_buyers);
        drawable[3] = ContextCompat.getDrawable(getContext(),R.drawable.ic_number_of_buyers);
        drawable[4] = ContextCompat.getDrawable(getContext(),R.drawable.ic_number_of_buyers);

        interpolators = new Interpolator[4];
        interpolators[0] = new AccelerateInterpolator();
        interpolators[1] = new DecelerateInterpolator();
        interpolators[2] = new AccelerateDecelerateInterpolator();
        interpolators[3] = new LinearInterpolator();

        dWidth = drawable[0].getIntrinsicWidth();
        dHeight = drawable[0].getIntrinsicHeight();

        lp = new LayoutParams(dWidth,dHeight);
        lp.addRule(ALIGN_PARENT_BOTTOM);
        lp.addRule(CENTER_HORIZONTAL);

        random = new Random();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //再此处才能准确获取到控件的宽高
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    /**
     * 创建可移动的View
     */
    public void startAnimation(){
        ImageView image = new ImageView(getContext());
        image.setImageDrawable(drawable[random.nextInt(5)]);
        image.setLayoutParams(lp);
        addView(image);
        start(image);
    }

    /**
     * 定时器，可以自动执行动画
     */
    public void startAutoAnimation(){
        isPlayingAnim = !isPlayingAnim ;
        if (isPlayingAnim){
            if (timer!=null){
                timer.cancel();
            }

            if (task!=null){
                task.cancel();
            }
        }else {
            timer = new Timer();
            task = new TimerTask() {
                @Override
                public void run() {
                    // 需要做的事:发送消息
                    Message message = handler.obtainMessage();
                    message.what = 1;
                    handler.sendMessage(message);
                }
            };
            timer.schedule(task, 0, 150); // 执行task,经过150ms循环执行
        }
    }


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                ImageView image = new ImageView(getContext());
                image.setImageDrawable(drawable[random.nextInt(5)]);
                image.setLayoutParams(lp);
                addView(image);
                start(image);
            }
        }
    };

    /**
     * view销毁之后调用，释放资源
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (timer!=null){
            timer.cancel();
        }
        if (task!=null){
            task.cancel();
        }
    }

    /**
     * 设置刚添加上imageview的属性动画,由小变大，逐渐清晰
     * @param image
     * @return
     */
    public AnimatorSet getInitAnimationSet(final ImageView image){
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(image,"scaleX",0.4f,1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(image,"scaleY",0.4f,1f);
        ObjectAnimator alpha = ObjectAnimator.ofFloat(image,"alpha",0.4f,1f);

        AnimatorSet animate = new AnimatorSet();
        animate.playTogether(scaleX,scaleY,alpha);
        animate.setDuration(500);
        return animate ;
    }
    /**
     * 动画效果
     * @param image
     */
    private AnimatorSet getRunAnimatorSet(final ImageView image) {
        AnimatorSet runSet = new AnimatorSet();
        PointF point0 = new PointF((mWidth-dWidth)/2,mHeight-dHeight); //起始点
        PointF point3 = new PointF(random.nextInt(AppUtils.getScreenWidth(getContext())),0); //终止点
        /**
         * 开始执行贝塞尔动画
         */
        TypeEvaluator evaluator = new BezierEvaluator(getPointF(2),getPointF(1));
        ValueAnimator bezier = ValueAnimator.ofObject(evaluator,point0,point3);
        bezier.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
                PointF pointF = (PointF) animation.getAnimatedValue();
                image.setX(pointF.x);
                image.setY(pointF.y);
                image.setAlpha(1-animation.getAnimatedFraction());
            }
        });
        runSet.play(bezier);
        runSet.setDuration(3000);
        return runSet;
    }

    /**
     * 合并执行两个动画
     * @param image
     */
    public void start(final ImageView image){
        AnimatorSet finalSet = new AnimatorSet();
        finalSet.setInterpolator(interpolators[random.nextInt(4)]);//实现随机变速
        finalSet.playSequentially(getInitAnimationSet(image), getRunAnimatorSet(image));
        finalSet.setTarget(image);
        finalSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                removeView(image);
            }
        });
        finalSet.start();
    }

    /**
     * 获取控制点
     * @param scale
     * @return
     */
    private PointF getPointF(int scale) {
        PointF pointF = new PointF();
        pointF.x = random.nextInt((mWidth - 100));//减去100 是为了控制 x轴活动范围,看效果
        //再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些  也可以用其他方法
        pointF.y = random.nextInt((mHeight - 100))/scale;
        return pointF;
    }
    public class BezierEvaluator implements TypeEvaluator<PointF> {
        /**
         * 这2个点是控制点
         */
        private PointF point1 ;
        private PointF point2 ;
        public BezierEvaluator(PointF point1 ,PointF point2 ) {
            this.point1 = point1 ;
            this.point2 = point2 ;
        }
        /**
         * @param t
         * @param point0 初始点
         * @param point3 终点
         * @return
         */
        @Override
        public PointF evaluate(float t, PointF point0, PointF point3) {
            PointF point = new PointF();
            point.x = point0.x*(1-t)*(1-t)*(1-t)
                    +3*point1.x*t*(1-t)*(1-t)
                    +3*point2.x*t*t*(1-t)*(1-t)
                    +point3.x*t*t*t ;
            point.y = point0.y*(1-t)*(1-t)*(1-t)
                    +3*point1.y*t*(1-t)*(1-t)
                    +3*point2.y*t*t*(1-t)*(1-t)
                    +point3.y*t*t*t ;
            return point;
        }
    }

}
