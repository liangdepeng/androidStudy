package ldp.example.com.android_demo.studydemo.dialog

import android.os.Bundle

import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ldp.base_lib.dialog.MyPopupWindow
import com.example.ldp.base_lib.dialog.PopupWindowHelper
import kotlinx.android.synthetic.main.activity_test_popup_dialog.*
import ldp.example.com.android_demo.R

class TestPopupDialogActivity : AppCompatActivity(), MyPopupWindow.PopupViewInterface {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_popup_dialog)
        initView()
        textView.post(object : Runnable {
            override fun run() {
                PopupWindowHelper.showVerticalAutomaticPopupView(this@TestPopupDialogActivity, textView, R.layout.dialog_popup_test, 0, 0)
            }
        })

    }

    private fun initView() {
        val popupWindow = MyPopupWindow.Builder(this)
                .setIsShowBg(true) // 是否背景变灰
                .setParams(1080, ViewGroup.LayoutParams.WRAP_CONTENT) // 设置宽高
                .setView(R.layout.dialog_popup_test) // 设置布局
                .setAnimationStyle(R.style.popup_dialog_anim2)
                .setUpShowAnimationStyle(R.style.popup_dialog_anim2) // 向上展开的动画
                .setDownShowAnimationStyle(R.style.popup_dialog_anim)  //向下展开的动画
                .setOutSideCancel(true) // 点击外面关闭
                .setBackGroundLevel(0.8f)  //背景颜色深度
                .setContentViewClickListener(this) // view操作回调
                .build()


        btn.setOnClickListener {
            // 自适应
            popupWindow.showVerticalAutomatic(it, 0, 0)

            val array = IntArray(2)
            btn.getLocationOnScreen(array)
            Log.e("x-y", array[0].toString() + "  " + array[1].toString())

        }

        popupBtn01.setOnClickListener {
            // 原本的用法 ，其他类似
            popupWindow.showAtLocation(it, Gravity.CENTER, 0, 0)
        }

        bottomPopup.setOnClickListener {
            PopupWindowHelper.bottomPopupView(this, it, R.layout.dialog_popup_test, null)
        }

        leftPopup.setOnClickListener {
            PopupWindowHelper.showLeftPopupView(this, leftPopup, R.layout.dialog_popup_test, null)
        }

        rightPopup.setOnClickListener {
            PopupWindowHelper.showRightPopupView(this, rightPopup, R.layout.dialog_popup_test, null)
        }

        auto_left_right_btn.setOnClickListener {
            PopupWindowHelper.showHorizontalAutomaticPopupView(this, it, R.layout.dialog_popup_test, 0, 0)
        }

        anchorViewTop.setOnClickListener {
            PopupWindowHelper.showTopPopupView(this, it, R.layout.dialog_popup_test, null)
        }

        anchorViewBottom.setOnClickListener {
            PopupWindowHelper.showBottomPopupView(this, it, R.layout.dialog_popup_test, null)
        }
    }

    override fun getPopupContentView(popupView: View?) {
        popupView?.let {
            it.findViewById<TextView>(R.id.test_tv).setOnClickListener {
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
