package ldp.example.com.android_demo.studydemo.zidingyikj;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ldp.base_lib.view.BezierView;

import ldp.example.com.android_demo.R;

/**
 * created by ldp at 2018/8/6
 */
public class circleActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
        BezierView bezierView = (BezierView) findViewById(R.id.bezierView);
        bezierView.startAutoAnimation();
    }
}
