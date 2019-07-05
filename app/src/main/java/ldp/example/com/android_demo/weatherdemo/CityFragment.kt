package ldp.example.com.android_demo.weatherdemo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import com.example.ldp.base_lib.base.MyBaseFragment
import ldp.example.com.android_demo.weatherdemo.bean.AddRessBean
import com.example.ldp.base_lib.http.HttpRequestInfo
import com.example.ldp.base_lib.http.onHttpResponseListner
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_my_weather.*
import kotlinx.android.synthetic.main.fragment_choose_city.*
import ldp.example.com.android_demo.R
import ldp.example.com.android_demo.constants.Constants
import ldp.example.com.android_demo.studydemo.utils.SPUtils
import ldp.example.com.android_demo.weatherdemo.bean.ResultBean
import org.litepal.crud.DataSupport
import java.lang.Exception

/**
 *  created by Da Peng at 2019/6/25
 */
class CityFragment : MyBaseFragment(), onHttpResponseListner, View.OnClickListener {

    private var httpRequestInfo: HttpRequestInfo? = null
    var cityList = arrayListOf<ResultBean>()
    var addressLevel = 0
    var cityId = 0
    var parentId = 0

    override fun initPresenter() {

    }

    override fun initData() {
        httpRequestInfo = HttpRequestInfo(this)
        if (SPUtils.get(context, "address", "") != "") {
//            cityList.clear()
//            val type = object : TypeToken<List<ResultBean>>() {}.type
//            cityList.addAll((Gson().fromJson((SPUtils.get(context, "address", "")).toString(), type)))
            queryProvince()
            recyclerView.adapter.notifyDataSetChanged()
        } else {
            startRequestInfo()
        }

    }

    override fun getLayoutResId(inflater: LayoutInflater?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_choose_city, null, false)
        return view
    }

    private var isZhiXiaShi = false

    override fun initView() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = CityAdapter(context, cityList)
        (recyclerView.adapter as CityAdapter).setOnItemClickListener(object : CityAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int, str: String?) {
//                showShortToast(str)
                parentId = cityList[position].parentid
                cityId = cityList[position].cityid

                var list = listOf<ResultBean>()
                when {
                    parentId == 0 -> {
                        addressLevel = 1
                        back_btn.visibility = View.VISIBLE
                        list = DataSupport.where("parentid = ?", cityId.toString()).find(ResultBean::class.java)
                        cityList.clear()
                        cityList.addAll(list)
                        city_name.text = str
                    }
                    parentId in 1..34 -> {
                        addressLevel = 2
                        list = DataSupport.where("parentid = ?", cityId.toString()).find(ResultBean::class.java)
                        if (list.isEmpty()) {
                            isZhiXiaShi = true
                            addressLevel = 3
                        } else {
                            cityList.clear()
                            cityList.addAll(list)
                            city_name.text = str
                        }
                    }
                    parentId > 34 -> {
                        addressLevel = 3
                    }
                }

                recyclerView.adapter.notifyDataSetChanged()
                if (addressLevel == 3) {
                    if (activity is MyWeatherActivity) {
                        val activity = activity as MyWeatherActivity
                        activity.drawerLayout.closeDrawers()
                        activity.requestWeatherData(str!!)
                        if (isZhiXiaShi) {
                            addressLevel = 1
                            isZhiXiaShi = false
                        } else {
                            addressLevel = 2
                        }
                    } else {
                        startActivity(Intent(context, MyWeatherActivity::class.java).putExtra("city", str))
                    }
                }
            }
        })
        back_btn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.back_btn) {
            cityList.clear()
            var list = listOf<ResultBean>()
            when (addressLevel) {
                0 -> {
//                    list = DataSupport.where("parentid = ?", 0.toString()).find(ResultBean::class.java)
                }
                1 -> {
                    list = DataSupport.where("parentid = ?", 0.toString()).find(ResultBean::class.java)
                    addressLevel = 0
                    city_name.text = "中国"
                    back_btn.visibility = View.GONE
                }
                2 -> {
                    if (parentId > 34) {
                        list = DataSupport.where("cityid = ?", parentId.toString()).find(ResultBean::class.java)
                        list = DataSupport.where("parentid = ?", list[0].parentid.toString()).find(ResultBean::class.java)
                    } else {
                        list = DataSupport.where("parentid = ?", parentId.toString()).find(ResultBean::class.java)
                    }
                    city_name.text = (DataSupport.where("cityid = ?", list[0].parentid.toString()).find(ResultBean::class.java))[0].city
                    addressLevel = 1
                }
            }
            cityList.addAll(list)
            recyclerView.adapter.notifyDataSetChanged()
        }
    }

    override fun startRequestInfo() {
        showProgressDialog()
        httpRequestInfo?.testAsyncTaskHttpRequest(Constants.ADDRESS_URL, AddRessBean::class.java)
    }

    override fun onSuccess(`object`: Any?) {

        val addressBean = `object` as AddRessBean

        SPUtils.put(context, "address", Gson().toJson(addressBean.result))

        cityList.clear()
        cityList.addAll(addressBean.result)

        SaveAddressInfo().execute(cityList)

//        recyclerView.adapter.notifyDataSetChanged()
    }

    override fun onFailed(e: Exception?) {
        hideProgressDialog()
    }

    @SuppressLint("StaticFieldLeak")
    inner class SaveAddressInfo : AsyncTask<ArrayList<ResultBean>, Int, Void>() {

        override fun doInBackground(vararg params: ArrayList<ResultBean>?): Void? {
            params[0]?.forEach { bean ->
                val cityInfoBean = ResultBean()
                cityInfoBean.city = bean.city
                cityInfoBean.citycode = bean.citycode
                cityInfoBean.cityid = bean.cityid
                cityInfoBean.parentid = bean.parentid
                cityInfoBean.save()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            queryProvince()
            recyclerView.adapter.notifyDataSetChanged()
            hideProgressDialog()
//            Html.fromHtml()
        }
    }

    fun queryProvince() {
        cityList.clear()
        var list = listOf<ResultBean>()
        list = DataSupport.where("parentid = ?", 0.toString()).find(ResultBean::class.java)
        cityList.addAll(list)
    }

}