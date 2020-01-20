package ldp.example.com.android_demo.livedata;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * created by Da Peng at 2019/6/27
 */
public class TestViewModel {

    private MutableLiveData<String> mData;
    private MutableLiveData<Integer> mData2;

    private MediatorLiveData mLiveDatas;

    public MutableLiveData<String> getDatas() {
        if (mData == null) {
            mData = new MutableLiveData<>();
        }
        return mData;
    }

    public MutableLiveData<Integer> getData2() {
        if (mData2 == null) {
            mData2 = new MutableLiveData<>();
        }
        return mData2;
    }

    public MediatorLiveData getLiveDatas(MutableLiveData data, Observer observer) {

        if (mLiveDatas == null) {
            mLiveDatas = new MediatorLiveData();
        }

        mLiveDatas.addSource(data, observer);
        return mLiveDatas;
    }

    public void removeDataObserver(MutableLiveData data, Observer observer) {
        if (mLiveDatas == null) {
            mLiveDatas = new MediatorLiveData();
        }
        mLiveDatas.removeSource(data);
        mLiveDatas.removeObserver(observer);
    }
}
