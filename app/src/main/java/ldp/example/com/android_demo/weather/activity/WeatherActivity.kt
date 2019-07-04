package ldp.example.com.android_demo.weather.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ldp.example.com.android_demo.R

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        Toast.makeText(this, intent.getStringExtra("citycode"), Toast.LENGTH_SHORT).show()
    }
}
