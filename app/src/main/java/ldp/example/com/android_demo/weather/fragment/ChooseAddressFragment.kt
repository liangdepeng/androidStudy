package ldp.example.com.android_demo.weather.fragment

import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.google.gson.Gson
import kotlinx.android.synthetic.main.choose_address_fragment.*
import ldp.example.com.android_demo.R
import ldp.example.com.android_demo.constants.Constants
import ldp.example.com.android_demo.studydemo.utils.SPUtils
import ldp.example.com.android_demo.weather.activity.WeatherActivity
import ldp.example.com.android_demo.weather.adapter.AddressAdapter
import ldp.example.com.android_demo.weather.dbbean.AddressBean
import org.litepal.crud.DataSupport
import org.xutils.common.Callback
import org.xutils.http.RequestParams
import org.xutils.x
import kotlin.collections.ArrayList

/**
 *  Error
 */
class ChooseAddressFragment : BaseFragment(), View.OnClickListener {

    var addressList: ArrayList<AddressBean.AddressDetailsBean> = arrayListOf()
    private val LEVEL_PROVINCE = 0
    private val LEVEL_CITY = 1
    private val LEVEL_COUNTY = 2
    var level = 0

    override fun initData() {
        requestData()
    }

    override fun initView(inflater: LayoutInflater?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.choose_address_fragment, null, false)
        val adapter = AddressAdapter(context, addressList)
        adapter.setOnItemClickListener(OnMyItemClickListener())
        val recyclerView = view.findViewById<RecyclerView>(R.id.addressRecycler)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        view.findViewById<Button>(R.id.back_btn1).setOnClickListener(this)
        return view
    }

    //    var index = 0
//    for (bean in addressList) {
//        val dataBean = AddressBean.AddressDetailsBean()
//        dataBean.city = addressList[index].city
//        dataBean.parentid = addressList[index].parentid
//        dataBean.cityid = addressList[index].cityid
//        dataBean.citycode = addressList[index].citycode
//        dataBean.saveThrows()
//        index++
//    }
    private fun requestData() {
        showProgressDialog()
        val params = RequestParams(Constants.ADDRESS_URL)
//        params.addBodyParameter()
        x.http().get(params, object : Callback.CommonCallback<String> {
            override fun onFinished() {
            }

            override fun onSuccess(result: String?) {
                closeProgressDialog()
                val addressBean = Gson().fromJson(result, AddressBean::class.java)
                addressList.clear()
                addressList.addAll(addressBean.result)

                // DataSupport.deleteAll(AddressBean.AddressDetailsBean::class.java)


                showData()
            }

            override fun onCancelled(cex: Callback.CancelledException?) {
            }

            override fun onError(ex: Throwable?, isOnCallback: Boolean) {
                closeProgressDialog()
            }
        })
    }

    private fun showData() {
        activity?.runOnUiThread {
            var index = 0
            for (bean in addressList) {
                val dataBean = AddressBean.AddressDetailsBean()
                dataBean.city = addressList[index].city
                dataBean.parentid = addressList[index].parentid
                dataBean.cityid = addressList[index].cityid
                dataBean.citycode = addressList[index].citycode
                dataBean.saveThrows()
                index++
            }
//            DataSupport.saveAll(addressList)
            title.text = "中国"
            addressList.clear()
            var list = DataSupport.where("parentid = ?", 0.toString())
                    .find(AddressBean.AddressDetailsBean::class.java)
            SPUtils.put(context, "provinceList", list)
            addressList.addAll(list)
            addressRecycler.adapter.notifyDataSetChanged()
            level = LEVEL_PROVINCE
        }
    }

    inner class OnMyItemClickListener : AddressAdapter.OnItemClickListener {
        override fun onItemClick(view: View?, position: Int) {
            var list: List<AddressBean.AddressDetailsBean>? = null
            if (level == LEVEL_PROVINCE) {
                list = DataSupport.where("parentid = ?", addressList[position].cityid)
                        .find(AddressBean.AddressDetailsBean::class.java)
                SPUtils.put(context, "cityList", list)
                level = LEVEL_CITY
                back_btn1.visibility = View.VISIBLE
            } else if (level == LEVEL_CITY) {
                list = DataSupport.where("parentid = ?", addressList[position].cityid)
                        .find(AddressBean.AddressDetailsBean::class.java)
                SPUtils.put(context, "countyList", list)
                level = LEVEL_COUNTY
                back_btn1.visibility = View.VISIBLE
            } else if (level == LEVEL_COUNTY) {
                startActivity(Intent(context, WeatherActivity::class.java).putExtra("city", addressList[position].city))
                return
            }

            addressList.clear()
            addressList.addAll(list!!)
            title.text = addressList[position].city
            addressRecycler.adapter.notifyDataSetChanged()

        }
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.back_btn1) {
            var list: List<AddressBean.AddressDetailsBean>? = null
            if (level == LEVEL_COUNTY) {
                list = SPUtils.get(context, "cityList", null) as ArrayList<AddressBean.AddressDetailsBean>
                level = LEVEL_CITY
            } else if (level == LEVEL_CITY) {
                list = SPUtils.get(context, "provinceList", null) as ArrayList<AddressBean.AddressDetailsBean>
                level = LEVEL_PROVINCE
                back_btn1.visibility = View.GONE
            }
            addressList.clear()
            addressList.addAll(list!!)
            val titles = DataSupport.where("cityid = ?", list[0].parentid).find(AddressBean.AddressDetailsBean::class.java)
            title.text = titles[0].city
            addressRecycler.adapter.notifyDataSetChanged()
        }
    }
}