package ldp.example.com.android_demo.livedata.fragment

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.view.LayoutInflater
import android.view.View
import android.widget.SeekBar
import com.example.ldp.base_lib.base.MyBaseFragment
import kotlinx.android.synthetic.main.fragment_live_data_one.*
import ldp.example.com.android_demo.R

/**
 *  created by Da Peng at 2019/6/26
 */
class LiveDataFragmentOne : MyBaseFragment() {

    lateinit var mFragmentViewModel: FragmentViewModel

    override fun initData() {

        mFragmentViewModel = ViewModelProviders.of(activity!!)[FragmentViewModel::class.java]
        mFragmentViewModel.data.observe(this, object : Observer<Int> {
            override fun onChanged(t: Int?) {
                seekBarThree.progress = t!!
            }
        })
    }

    override fun getLayoutResId(inflater: LayoutInflater?): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_live_data_one, null, false)
    }

    override fun initView() {
        seekBarOne.max = 100
        seekBarThree.max = 100
        seekBarOne.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    // seekBar 拖动 模拟改变数据源
                    mFragmentViewModel.changeData(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun initPresenter() {

    }
}