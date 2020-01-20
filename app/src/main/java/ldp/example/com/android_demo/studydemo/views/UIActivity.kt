package ldp.example.com.android_demo.studydemo.views

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.google.android.material.tabs.TabLayout.MODE_FIXED
import kotlinx.android.synthetic.main.activity_base_ui_layout.*
import ldp.example.com.android_demo.R

class UIActivity : AppCompatActivity() {

    private val titles = arrayListOf<String>()
    private val fragments = arrayListOf<Fragment>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_ui)
        setContentView(R.layout.activity_base_ui_layout)
        bindFragments()
        initView()
        //initView2()
    }

    private fun bindFragments() {
        titles.add("textView")
        fragments.add(TextViewFragment())

        titles.add("textView")
        fragments.add(TextViewFragment())

        titles.add("textView")
        fragments.add(TextViewFragment())

        titles.add("广播")
        fragments.add(BroadcastReceiverFragment())
    }

    private fun initView() {
        viewPager.adapter = ViewPagerAdapter(titles, fragments)
        viewPager.offscreenPageLimit = 1

        tabLayout.setupWithViewPager(viewPager)
        tabLayout.tabMode = MODE_FIXED
    }

    inner class ViewPagerAdapter(titles: List<String>, fragments: List<Fragment>) : FragmentStatePagerAdapter(supportFragmentManager) {

        private val fragments = fragments
        private val titles = titles

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titles[position]
        }
    }
}
