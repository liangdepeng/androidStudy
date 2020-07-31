package com.example.ldp.base_lib.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import com.example.ldp.base_lib.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @author 14515
 */
@SuppressLint("AppCompatCustomView")
public class PassWordKuangView extends EditText {

    //外边框颜色
    private int borderColor = 0xFFCCCCCC;
    //外边框线的粗细
    private float borderWidth = 5;
    //外边框圆角半径
    private float borderRadius = 3;
    //中间分割线粗细
    private float dividerWidth = 3;
    //密码长度，默认6个字符
    private int passwordLength = 6;
    //密码文字颜色
    private int passwordColor = 0xFFCCCCCC;
    //密码圆点的半径
    private float passwordRadius = 3;
    //画笔
    private Paint paint;
    //整个view的宽，高
    private int width, height;

    public PassWordKuangView(Context context) {
        this(context,null);
    }

    public PassWordKuangView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PassWordKuangView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        borderWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderWidth, dm);
        borderRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, borderRadius, dm);
        passwordLength = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordLength, dm);
        passwordRadius = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, passwordRadius, dm);

        //获得自定义属性的值
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PasswordInputView, 0, 0);
        borderColor = a.getColor(R.styleable.PasswordInputView_pivBorderColor, borderColor);
        borderWidth = a.getDimension(R.styleable.PasswordInputView_pivBorderWidth, borderWidth);
        borderRadius = a.getDimension(R.styleable.PasswordInputView_pivBorderRadius, borderRadius);
        passwordLength = a.getInt(R.styleable.PasswordInputView_pivPasswordLength, passwordLength);
        passwordColor = a.getColor(R.styleable.PasswordInputView_pivPasswordColor, passwordColor);
        passwordRadius = a.getDimension(R.styleable.PasswordInputView_pivPasswordRadius, passwordRadius);
        dividerWidth = a.getDimension(R.styleable.PasswordInputView_dividerWidth, dividerWidth);
        a.recycle();

        paint = new Paint(ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        width = getWidth();
        height = getHeight();
        drawBorder(canvas);
        drawDivider(canvas);
        drawCircle(canvas);

    }

    //绘制外边框
    private void drawBorder(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(borderWidth);
        RectF rectF = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rectF, borderRadius, borderRadius, paint);
    }

    //绘制分割线
    private void drawDivider(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(borderColor);
        paint.setStrokeWidth(dividerWidth);
        for (int i = 1; i < passwordLength; i++) {
            int x = i * (width / passwordLength);
            canvas.drawLine(x, 0, x, height, paint);
        }
    }
    //绘制圆点
    private void drawCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(passwordColor);
        String content = getText().toString().trim();
        for (int i = 0; i < content.length(); i++) {
            int cx = width / passwordLength / 2 + i * width / passwordLength;
            int cy = height / 2;
            canvas.drawCircle(cx, cy, width / passwordLength / 8, paint);
        }
    }
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        invalidate();
        //密码输入完成后做的操作，例如密码验证，页面跳转等
        if (text.length() == passwordLength) {
            if (onCompleteListener != null) {
                onCompleteListener.onComplete();
            }
        }
    }
    private OnCompleteListener onCompleteListener;
    public void setOnCompleteListener(OnCompleteListener onCompleteListener) {
        this.onCompleteListener = onCompleteListener;
    }
    public interface OnCompleteListener {
        void onComplete();
    }

}
