package ldp.example.com.android_demo.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main2.*
import ldp.example.com.android_demo.R
import com.example.ldp.base_lib.base.BasePermissionActivity


class MainPermissionActivity2 : BasePermissionActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        initData()
    }

    private fun initData() {
        val list = arrayListOf<ClassesBean>()
        for (c in ClassEnum.values()) {
            list.add(ClassesBean(c.info, c.clazz))
        }
        initView(list)
    }

    private fun initView(list: ArrayList<ClassesBean>) {
        main_recycler.layoutManager = LinearLayoutManager(this@MainPermissionActivity2)
        main_recycler.addItemDecoration(MyItemDecoration())
        val adapter = MyAdapter(this)
        adapter.setData(list)
        main_recycler.adapter = adapter
    }

    class MyAdapter(var context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

        var list: List<ClassesBean> = listOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
            holder.txt.text = list[position].info
            holder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    context.startActivity(Intent(context, list[position].clazz))
                }
            })
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var txt: TextView = itemView.findViewById(R.id.text)
        }

        fun setData(list: List<ClassesBean>) {
            this.list = list
            notifyDataSetChanged()
        }
    }

    class MyItemDecoration : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect?.bottom = 2
        }



        override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
            val childCount = parent?.childCount
            val left = parent?.paddingLeft?.toFloat()
            val right = parent?.width?.minus(parent.paddingRight)?.toFloat()

            for (i in 0..childCount!!) {
                val view = parent.getChildAt(i)
                view?.let {
                    val top = view.bottom.toFloat()
                    val bottom = (view.bottom + 2).toFloat()
                    c?.drawRect(10.toFloat(), top, parent.width.toFloat() - 10, bottom, initPaint())
                }

            }
        }

        private fun initPaint(): Paint {
            val paint = Paint()
            paint.color = Color.WHITE
            return paint
        }
    }
}
