package ldp.example.com.android_demo.livedata;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.example.ldp.base_lib.bean.WeatherBean;
import com.example.ldp.base_lib.http.HttpRequestInfo;
import com.example.ldp.base_lib.http.onHttpResponseListner;

import ldp.example.com.android_demo.constants.Constants;


/**
 * created by Da Peng at 2019/6/26
 */
public class TestViewModel2 extends ViewModel implements onHttpResponseListner {

    /**
     *  需要观察的数据
     */
    private MutableLiveData<WeatherBean> mData = new MutableLiveData<>();

    private HttpRequestInfo mHttpRequestInfo = new HttpRequestInfo(this);
    private String cityName;
    private boolean zaoShuJu = false;

    public TestViewModel2(String cityName) {
        this.cityName = cityName;
    }

    public MutableLiveData<WeatherBean> getData() {
        return mData;
    }

    public void requestData() {
        if (!zaoShuJu){
            mData.setValue(zaoShuJu());
            zaoShuJu = true;
        }else {
            zaoShuJu = false;
            mHttpRequestInfo.testAsyncTaskHttpRequest(Constants.WEATHER_URL + cityName, WeatherBean.class);
        }
    }

    @Override
    public void onSuccess(Object object) {
        if (object instanceof WeatherBean) {
            WeatherBean weatherBean = (WeatherBean) object;
            mData.setValue(weatherBean);
        }
    }

    private WeatherBean zaoShuJu(){
        WeatherBean bean = new WeatherBean();
        bean.setMsg("LiveData 数据源 数据改变 测试 ok ok 测试");
        return bean;
    }

    @Override
    public void onFailed(Exception e) {

    }

    /**
     * 初始化 ViewModel 如果需要传参，则实现ViewModelProvider.Factory接口
     */
    static class TestViewModelFactory implements ViewModelProvider.Factory {

        private String cityName;

        TestViewModelFactory(String cityName){
            this.cityName = cityName;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new TestViewModel2(cityName);
        }
    }
}
