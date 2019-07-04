package ldp.example.com.android_demo.studydemo.pic

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import com.example.ldp.base_lib.base.MyBaseActivity
import kotlinx.android.synthetic.main.activity_image_capture.*
import ldp.example.com.android_demo.R
import org.xutils.x

class ImageCaptureActivity : MyBaseActivity() {

    private val Picture = 100

    override fun getLayoutResId(): Int {
        return R.layout.activity_image_capture
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun startRequestInfo() {
    }

    override fun initPresenter() {
    }

    override fun initView() {
        choose_pic.setOnClickListener {
            startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI), Picture)
        }
    }

    override fun initData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Picture && resultCode == Activity.RESULT_OK && data != null) {
            val selectImage = data.data
            val filesPathColumns: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = this.contentResolver.query(selectImage, filesPathColumns, null, null, null)
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndex(filesPathColumns[0])
            val url = cursor.getString(columnIndex)
            cursor.close()
            x.image().bind(image, url)
        }
    }
}


