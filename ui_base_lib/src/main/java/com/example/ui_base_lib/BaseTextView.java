package com.example.ui_base_lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class BaseTextView extends androidx.appcompat.widget.AppCompatTextView {

    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
