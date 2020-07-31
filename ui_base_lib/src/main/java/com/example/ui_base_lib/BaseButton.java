package com.example.ui_base_lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class BaseButton extends androidx.appcompat.widget.AppCompatButton {
    public BaseButton(Context context) {
        super(context);
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
