package ldp.example.com.android_demo.livedata.fragment

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.ldp.base_lib.http.HttpRequestInfo
import com.example.ldp.base_lib.http.onHttpResponseListner
import java.lang.Exception

/**
 *  created by Da Peng at 2019/6/26
 *
 *  fragment 1 和 fragment 2 共用一个 FragmentViewModel，实现数据共享
 *
 */
class FragmentViewModel : ViewModel(), onHttpResponseListner {

    var data: MutableLiveData<Int> = MutableLiveData()
    var httpRequestInfo: HttpRequestInfo = HttpRequestInfo(this)

    /**
     * 数据更新，相应UI界面在可更新状态时也会更新数据
     */
    fun changeData(progress: Int) {
        data.value = progress
    }

    /**
     * 被观察的数据
     */
    fun getDatas(): MutableLiveData<Int> {
        return data
    }

    override fun onSuccess(`object`: Any?) {
    }

    override fun onFailed(e: Exception?) {
    }

}