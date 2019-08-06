package ldp.example.com.android_demo.studydemo.kotlin

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.ldp.base_lib.utils.LogUtils
import com.example.ldp.base_lib.view.MyRecyclerViewViewHolder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_kotlin.*
import ldp.example.com.android_demo.R
import ldp.example.com.android_demo.studydemo.utils.Constants
import org.xutils.x
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class KotlinVideoPlayerActivity : AppCompatActivity(), View.OnClickListener {

    var result = StringBuilder()
    var list: ArrayList<TestBean.TrailersBean> = arrayListOf()
    private val progressDialog: ProgressDialog by lazy {
        ProgressDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        initView()
    }

    private fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MyListAdapter(this, list)
        request_btn.setOnClickListener(this)
        clear_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.request_btn -> {
                requestData()
            }
            R.id.clear_btn -> {
                clearData()
            }
        }
    }

    private fun requestData() {
        showCustomDialog()

        Thread(object : Runnable {
            lateinit var connection: HttpURLConnection

            override fun run() {
                try {
                    val url = URL(Constants.INTERNET_URL)
                    Thread.sleep(1_000)
                    LogUtils.e("netUrl", url.toString())
                    connection = url.openConnection() as HttpURLConnection
                    connection.requestMethod = "GET"
                    connection.readTimeout = 5000
                    connection.connectTimeout = 5000
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    var line: String? = null
                    result = StringBuilder()
                    while ({ line = reader.readLine();line }() != null) {
                        result.append(line)
                    }
                    showData(result)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    connection.disconnect()
                }

            }
        }).start()
    }

    private fun clearData() {
        test_txt.text = ""
        list.clear()
        recyclerView.adapter.notifyDataSetChanged()
    }

    private fun showData(result: StringBuilder) {
        cancelCustomDialog()
        runOnUiThread {
            test_txt.text = result.toString()
            val s = result.toString()
            val json = Gson().fromJson(s, TestBean::class.java)
            list.clear()
            list.addAll(json.trailers)
            recyclerView.adapter.notifyDataSetChanged()
        }

    }


    class MyListAdapter(var context: Context, var list: ArrayList<TestBean.TrailersBean>) : RecyclerView.Adapter<MyListAdapter.ViewHolder>() {


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val bean = list[position]
            holder.txt.text = bean.movieName
            holder.summary.text = bean.summary ?: bean.videoTitle
            x.image().bind(holder.image, bean.coverImg)
            holder.itemView.setOnClickListener {
                Toast.makeText(context, bean.movieName + " onClicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SystemVideoPlayerActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("video_list", list)
                intent.run {
                    putExtras(bundle)
                    putExtra("video_position", position)
                    putExtra("video_url", bean.url)
                    putExtra("video_heightUrl", bean.hightUrl)
                }
                context.startActivity(intent)
            }
        }

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var txt: TextView = itemView.findViewById(R.id.txt)
            var summary: TextView = itemView.findViewById(R.id.summary)
            var image: ImageView = itemView.findViewById(R.id.image)
        }

    }

    fun showCustomDialog() {
        if (!progressDialog.isShowing) {
            progressDialog.setMessage("正在加载中...")
            progressDialog.show()
        }
    }

    fun cancelCustomDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    class MyListAdapter2(var context: Context, var list: ArrayList<TestBean.TrailersBean>) : RecyclerView.Adapter<MyRecyclerViewViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRecyclerViewViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false)
            return MyRecyclerViewViewHolder(view)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: MyRecyclerViewViewHolder, position: Int) {
            val bean = list[position]
            holder.setText(R.id.txt,bean.movieName)
            //holder.txt.text = bean.movieName
            holder.setText(R.id.summary,bean.summary?:bean.videoTitle)
            //holder.summary.text = bean.summary ?: bean.videoTitle
            x.image().bind(holder.getView(R.id.image), bean.coverImg)
            holder.itemView.setOnClickListener {
                Toast.makeText(context, bean.movieName + " onClicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(context, SystemVideoPlayerActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("video_list", list)
                intent.run {
                    putExtras(bundle)
                    putExtra("video_position", position)
                    putExtra("video_url", bean.url)
                    putExtra("video_heightUrl", bean.hightUrl)
                }
                context.startActivity(intent)
            }
        }

    }
}
