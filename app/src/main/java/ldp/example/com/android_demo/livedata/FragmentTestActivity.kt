package ldp.example.com.android_demo.livedata


import android.os.Bundle
import com.example.ldp.base_lib.base.BaseActivity
import ldp.example.com.android_demo.R
import ldp.example.com.android_demo.livedata.fragment.LiveDataMvpFragmentOne
import ldp.example.com.android_demo.livedata.fragment.LiveDataMvpFragmentTwo

class FragmentTestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        initView()
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout1,LiveDataMvpFragmentOne())
        transaction.replace(R.id.frameLayout2,LiveDataMvpFragmentTwo())
        transaction.commit()
    }

}
