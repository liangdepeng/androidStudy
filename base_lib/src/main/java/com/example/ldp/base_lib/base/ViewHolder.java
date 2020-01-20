package com.example.ldp.base_lib.base;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * created by Da Peng at 2019/7/30
 */
public class ViewHolder{

    private SparseArray<View> mViewSparseArray;
    private View convertView;

    private ViewHolder(View view) {
        convertView = view;
        mViewSparseArray = new SparseArray<>();
    }

    public static ViewHolder init(View view) {
        return new ViewHolder(view);
    }

    public <T extends View> T getView(int viewId) {
        View view = mViewSparseArray.get(viewId);
        if (view == null) {
            view = convertView.findViewById(viewId);
            mViewSparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView() {
        return convertView;
    }

    /**
     * Sets the text to be displayed using a string resource identifier.
     *
     * @param stringResId the resource identifier of the string resource to be displayed
     * @attr ref android.R.styleable#TextView_text
     * setText(CharSequence)
     */
    public ViewHolder setText(int viewId, @StringRes int stringResId) {
        return setText(viewId, null, stringResId, -1);
    }

    public ViewHolder setText(int viewId, CharSequence text) {
        return setText(viewId, text, -1);
    }

    public ViewHolder setText(int viewId, CharSequence text, int colorId) {
        return setText(viewId, text, -1, colorId);
    }

    private ViewHolder setText(int viewId, CharSequence text, @StringRes int stringResId, int colorId) {
        TextView textView = getView(viewId);
        if (text != null) {
            textView.setText(text);
        } else if (stringResId != -1) {
            textView.setText(stringResId);
        }

        if (colorId != -1) {
            textView.setTextColor(colorId);
        }
        return this;
    }

    public ViewHolder setTextColor(int viewId, int colorId) {
        TextView textView = getView(viewId);
        textView.setTextColor(colorId);
        return this;
    }

    /**
     * Sets the horizontal alignment of the text and the
     * vertical gravity that will be used when there is extra space
     * in the TextView beyond what is required for the text itself.
     *
     * @see android.view.Gravity
     */
    public ViewHolder setTextGravity(int viewId, int gravity) {
        TextView textView = getView(viewId);
        textView.setGravity(gravity);
        return this;
    }

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     */
    public ViewHolder setOnClickListener(int viewId, View.OnClickListener onClickListener) {
        if (onClickListener != null) {
            View view = getView(viewId);
            view.setOnClickListener(onClickListener);
        }
        return this;
    }

    /**
     * Register a callback to be invoked when this view is clicked and held. If this view is not
     * long clickable, it becomes long clickable.
     */
    public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener onLongClickListener) {
        if (onLongClickListener != null) {
            View view = getView(viewId);
            view.setOnLongClickListener(onLongClickListener);
        }
        return this;
    }

    /**
     * Sets a drawable as the content of this ImageView.
     */
    public ViewHolder setImagineLocalRes(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImagineDrawable(int viewId, Drawable drawable) {
        ImageView image = getView(viewId);
        image.setImageDrawable(drawable);
        return this;
    }

    /**
     * Set the background to a given resource. The resource should refer to
     * a Drawable object or 0 to remove the background.
     *
     * @param resId The identifier of the resource.
     * @attr ref android.R.styleable#View_background
     */
    public ViewHolder setViewBackgroundRes(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackgroundResource(resId);
        return this;
    }

    /**
     * Android 4.1 之后
     *
     * @param viewId     viewId
     * @param background background
     * @return ViewHolder
     */
    public ViewHolder setViewBackgroundDrawable(int viewId, Drawable background) {
        View view = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(background);
        }
        return this;
    }

    /**
     * Android 4.1 版本之后 设置图片透明度
     *
     * @param viewId viewId
     * @param alpha  0 ~ 255
     * @return this
     */
    public ViewHolder setImagineAlpha(int viewId, int alpha) {
        ImageView imageView = getView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imageView.setImageAlpha(alpha);
            return this;
        } else {
            return setViewAlpha(viewId, ((float) alpha) / ((float) 255));
        }
    }

    /**
     * Sets the opacity of the view to a value from 0 to 1, where 0 means the view is
     * completely transparent and 1 means the view is completely opaque.
     * <p>
     * setting alpha to a translucent value (0 < alpha < 1)
     */
    public ViewHolder setViewAlpha(int viewId, float alpha) {
        View view = getView(viewId);
        view.setAlpha(alpha);
        return this;
    }

    /**
     * Set the visibility state of this view.
     *
     * @param visibility One of {VISIBLE}, {INVISIBLE}, or {GONE}.
     * @attr ref android.R.styleable#View_visibility
     */
    public ViewHolder setViewVisibility(int viewId, int visibility) {
        View view = getView(viewId);
        view.setVisibility(visibility);
        return this;
    }

    /**
     * Sets the background color for this view.
     *
     * @param colorId the color of the background
     */
    public ViewHolder setViewBackgroundColor(int viewId, int colorId) {
        View view = getView(viewId);
        view.setBackgroundColor(colorId);
        return this;
    }

    /**
     * Set the enabled state of this view. The interpretation of the enabled
     * state varies by subclass.
     *
     * @param isEnabled True if this view is enabled, false otherwise.
     */
    public ViewHolder setViewEnabled(int viewId, boolean isEnabled) {
        View view = getView(viewId);
        view.setEnabled(isEnabled);
        return this;
    }

    public ViewHolder setImagineByNetUrl(Context context, int viewId, String netResUrl, int placeholder) {
        ImageView imageView = getView(viewId);
        glideToImagine(context, imageView, netResUrl, placeholder);
        return this;
    }

    private void glideToImagine(Context context, ImageView image, String netResUrl, int placeholder) {
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(placeholder)
                .centerCrop();
        //      .error(errorImagineId)    , int errorImagineId
        Glide.with(context).load(netResUrl)
                .apply(requestOptions)
                .into(image);
    }


}

//工作中有时候经常会遇到控制按钮是否可点击的时候，setEnabled() 和 setClickable() 都可以做到，只要将它们设置成false ，按钮就不可点击，设置成true，按钮就可以点击。
//
//它们的区别在于：
//
//setClickable():设置成true时，按钮为可点击，设置为false时，按钮不可点击，不能响应点击事件，但此时如果setEnabled()为true，那么按钮即使不可点击（setClickable()为false），也会产生变化（一闪一闪）。
//
//setEnabled():设置成true时，相当于激活了按钮，按钮的状态不再是死的，而是会对触摸或者点击产生反应，并且可以响应一些触发事件。而设置成false时，按钮是灰色的，无论是否可点击（即使将setClickable()设置成true），都无法响应任何触发事件。
//
//其实区别就在上面说的几个小地方，总的来看，setEnabled()相当于总开关，控制着按钮的状态，而setClickable()相当于具体的某个开关，控制这个开关是否可以点击。

