package ldp.example.com.android_demo.studydemo.views

import android.content.Context
import android.graphics.Color
import android.os.Bundle

import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_ui.*
import ldp.example.com.android_demo.R

/**
 *  @author  mini
 *  @date 2019/10/23
 */
class TextViewFragment : Fragment(){

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val textBuilder = StringBuilder()
        for (i in 0..20) {
            textBuilder.append("好友$i ,")
        }
        val str = textBuilder.substring(0, textBuilder.lastIndexOf(",")).toString()
        tv_3.movementMethod = LinkMovementMethod.getInstance()
        tv_3.setText(addClickPart(str), TextView.BufferType.SPANNABLE)
    }

    private fun addClickPart(str: String): SpannableStringBuilder {
        val imageSpan = context?.let { ImageSpan(it, R.drawable.ic_number_of_buyers) }

        val spanStr = SpannableString("p.")
        spanStr.setSpan(imageSpan, 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)

        val spannableStringBuilder = SpannableStringBuilder(spanStr)
        spannableStringBuilder.append(str)
        val list = str.split(",")
        if (list.isNotEmpty()) {
            for (i in list.indices) {
                val name = list[i]
                val startIndex = str.indexOf(name) + spanStr.length
                spannableStringBuilder.setSpan(object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        Toast.makeText(context, name, Toast.LENGTH_SHORT).show()
                    }

                    override fun updateDrawState(ds: TextPaint) {
                        super.updateDrawState(ds)
                        ds.color = Color.BLUE
                        ds.isUnderlineText = false
                    }
                }, startIndex, startIndex + name.length, 0)
            }
        }
        spannableStringBuilder.append("...等" + list.size * 3 + "人觉得很棒")
        return spannableStringBuilder
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TextViewFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TextViewFragment().apply {
                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}