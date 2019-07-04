package ldp.example.com.android_demo.weatherdemo

import android.os.Bundle
import android.view.View
import com.example.ldp.base_lib.base.MyBaseActivity
import com.example.ldp.base_lib.http.HttpRequestInfo
import com.example.ldp.base_lib.http.onHttpResponseListner
import com.example.ldp.base_lib.utils.LogUtils
import kotlinx.android.synthetic.main.activity_my_weather.*
import ldp.example.com.android_demo.R
import ldp.example.com.android_demo.constants.Constants
import com.example.ldp.base_lib.utils.AppUtils
import ldp.example.com.android_demo.weatherdemo.bean.AddRessBean
import com.example.ldp.base_lib.bean.WeatherBean
import ldp.example.com.android_demo.weatherdemo.bean.ResultBean
import java.lang.Exception

class MyWeatherActivity : MyBaseActivity(),
        onHttpResponseListner, View.OnClickListener {

    var httpInfo: HttpRequestInfo? = HttpRequestInfo(this)
    var list = arrayListOf<ResultBean>()
    var s = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.getStringExtra("city") != null) {
            s = intent.getStringExtra("city")
            requestWeatherData(s)
        }
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_my_weather
    }

    override fun initPresenter() {

    }

    override fun startRequestInfo() {

    }

    override fun initView() {
        weather_btn.setOnClickListener(this)
    }

    override fun initData() {

    }

    fun requestWeatherData(cityName: String) {
        showProgressDialog()
        httpInfo?.testAsyncTaskHttpRequest(Constants.WEATHER_URL + cityName, WeatherBean::class.java)
    }

    override fun onClick(v: View?) {
        showProgressDialog()
//        showLongToast("clicked")
        // httpInfo?.requestInfo(Constants.ADDRESS_URL, AddRessBean::class.java)

        httpInfo?.testAsyncTaskHttpRequest(Constants.ADDRESS_URL, AddRessBean::class.java)

    }

    override fun onSuccess(`object`: Any?) {
        LogUtils.e("thread", AppUtils.getThreadInfo())
        if (`object` is AddRessBean) {
            text_weather.text = (`object` as AddRessBean).toString()
        } else if (`object` is WeatherBean) {
            text_weather.text = (`object` as WeatherBean).toString()
        }

        hideProgressDialog()
//        showLongToast("success!")
    }

    override fun onFailed(e: Exception?) {
        showLongToast("failed!")
        hideProgressDialog()
    }
}
