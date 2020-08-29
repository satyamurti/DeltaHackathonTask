package com.mrspd.photoeditor.Gallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log.d
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.mrspd.photoeditor.PhotoEditor
import com.mrspd.photoeditor.R
import java.util.*

class PhotoFullSize : AppCompatActivity(),View.OnClickListener {


    lateinit var mPager: ViewPager
    var currentPage : Int = 0
    var numPages : Int = 0
    lateinit var imageList: ArrayList<String>
     lateinit var editBtn : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_full_size)
        editBtn = findViewById(R.id.btn_edit)
        editBtn.setOnClickListener(this)

        var bundle : Bundle? = this.intent.extras
        imageList = bundle?.getStringArrayList("photoList") as ArrayList<String>
        currentPage = intent.getIntExtra("pos",0)
        mPager = findViewById(R.id.photo_img_view_container)
        mPager.adapter = SlidingImageAdapter(this,imageList,this)
        mPager.setCurrentItem(currentPage, true)
        numPages = imageList.size
        setActionBarTitle(currentPage)



    }


    override fun onClick(v: View?) {
        if(v!!.id==R.id.btn_edit) {
            var intent = Intent(this, PhotoEditor::class.java)
            intent.putExtra("curPic",imageList[mPager.currentItem])
//            Log.v("PIC",imageList[currentPage])
            startActivity(intent)
            d("gghh","Hey")
            d("gghh","file:"+imageList[mPager.currentItem])

        }

    }

    fun setActionBarTitle(cur: Int) {
//        supportActionBar!!.title = cur.toString() + " of " + numPages.toString()
    }


}
