package com.example.ldp.base_lib.view.animator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ldp.base_lib.R;
import com.example.ldp.base_lib.base.BaseActivity;


public class AnimatorActivity extends BaseActivity implements View.OnClickListener {

    private AnimatorView mAnimatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        initView();
    }


    private void initView() {
        mAnimatorView = (AnimatorView) findViewById(R.id.animatorView);
        Button translationX = (Button) findViewById(R.id.translationX);
        translationX.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.translationX) {
            mAnimatorView.animate().translationX(900)
                    .setDuration(5000);

        }
    }
}
