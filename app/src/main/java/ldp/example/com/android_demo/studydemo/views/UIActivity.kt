package ldp.example.com.android_demo.studydemo.views

import android.os.Bundle
import android.support.design.widget.TabLayout.MODE_FIXED
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
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
