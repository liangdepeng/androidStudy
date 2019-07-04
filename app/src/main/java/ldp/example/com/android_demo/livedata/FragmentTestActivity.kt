package ldp.example.com.android_demo.livedata

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ldp.example.com.android_demo.R
import ldp.example.com.android_demo.livedata.fragment.LiveDataFragmentOne
import ldp.example.com.android_demo.livedata.fragment.LiveDataFragmentTwo

class FragmentTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_test)
        initView()
    }

    private fun initView() {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout1,LiveDataFragmentOne())
        transaction.replace(R.id.frameLayout2,LiveDataFragmentTwo())
        transaction.commit()
    }

}
