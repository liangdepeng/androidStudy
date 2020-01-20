package ldp.example.com.android_demo.livedata.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.ldp.base_lib.base.MyBaseFragment
import kotlinx.android.synthetic.main.fragment_livedata_two.*
import ldp.example.com.android_demo.R

/**
 *  created by Da Peng at 2019/6/26
 */
class LiveDataFragmentTwo :MyBaseFragment() {

    lateinit var mFragmentViewModel:FragmentViewModel

    override fun initView() {
        seekBarTwo.max = 100
        mFragmentViewModel = ViewModelProviders.of(activity!!)[FragmentViewModel::class.java]
        mFragmentViewModel.getDatas().observe(this,object : Observer<Int> {
            override fun onChanged(t: Int?) {
                seekBarTwo.progress = t!!
            }
        })
    }

    override fun initPresenter() {
    }

    override fun initData() {
    }

    override fun getLayoutResId(inflater: LayoutInflater?): View {
        return View.inflate(context, R.layout.fragment_livedata_two,null)
    }
}