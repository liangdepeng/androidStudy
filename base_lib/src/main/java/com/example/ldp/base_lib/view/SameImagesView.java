package com.example.ldp.base_lib.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ldp.base_lib.R;
import com.example.ldp.base_lib.utils.AppUtils;
import com.example.ldp.base_lib.utils.LogUtils;
import com.example.ldp.base_lib.utils.ViewUtils;

/**
 * created by Da Peng at 2019/8/6
 */
public class SameImagesView extends View {

    private int imageResId;
    private int count;
    private int orientation;
    public static int HORIZONTAL = 1;
    public static int VERTICAL = -1;
    private Bitmap bitmap;

    public SameImagesView(Context context) {
        this(context, null);
    }

    public SameImagesView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SameImagesView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (imageResId == 0 && bitmap == null) {
            return;
        }

        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(getResources(), imageResId);
        }

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        for (int i = 0; i < count; i++) {
            float left = i * (width + 20);
            float top = i * (height + 20);
            if (orientation == HORIZONTAL) {
                if (left > getWidth()) {
                    return;
                }
                canvas.drawBitmap(bitmap, left, 3, null);
            } else if (orientation == VERTICAL) {
                if (top > getHeight()) {
                    return;
                }
                canvas.drawBitmap(bitmap, 20, top + 3, null);
            } else {
                //  Paint paint = new Paint();
                //  Path path = new Path();
                //  path.addArc();
                //  canvas.drawPath(path, paint);
                // 其他...
            }
            LogUtils.w("draw_thread", AppUtils.getThreadInfo());
            LogUtils.e("width", String.valueOf(getWidth()) + " --- " + left);
        }
    }

    public void setImageRes(int imageResId, int count) {
        setResInvalidate(imageResId, null, count, HORIZONTAL);
    }

    /**
     * @param view  view
     * @param count 数量
     * @ warning 可见视图
     */
    public void setViewRes(View view, int count) {
        setResInvalidate(0, ViewUtils.createBitmap(view), count, orientation);
    }

    public void setImageRes(int imageResId, int count, int orientation) {
        setResInvalidate(imageResId, null, count, orientation);
    }

    private void setResInvalidate(int imageResId, Bitmap bitmap, int count, int orientation) {
        this.orientation = orientation;
        this.bitmap = bitmap;
        this.imageResId = imageResId;
        this.count = count;
        invalidate();
    }

    public void removeAllViews() {
        setImageRes(imageResId, 0, 0);
    }

}

//        if (imageResId != 0) {
//            Drawable drawable = getContext().getResources().getDrawable(imageResId);
//            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
//            canvas.drawBitmap(bitmap, 0, 0, null);
//        }
