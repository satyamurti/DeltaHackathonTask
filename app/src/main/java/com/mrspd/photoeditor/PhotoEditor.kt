package com.mrspd.photoeditor

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log.d
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import com.mrspd.photoeditor.canvas.BlurView
import com.mrspd.photoeditor.canvas.CropClass
import com.mrspd.photoeditor.canvas.DrawingView
import kotlinx.android.synthetic.main.activity_photo_editor.*
import java.io.File
import java.io.FileOutputStream


class PhotoEditor : AppCompatActivity() {
    lateinit var curUri: String
    var drawView: RelativeLayout? = null
    var drawingView: DrawingView? = null
    lateinit var myImg :Bitmap


    companion object {
        var left = 100f
        var right = 600f
        var top = 100f
        var bottom = 500f


    }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_editor)
        curUri = intent.getStringExtra("curPic").toString()
        val bitmap = BitmapFactory.decodeFile(
            curUri
        )
        val dstBitmap = Bitmap.createBitmap(
            bitmap.width + 12 * 2,  // Width
            bitmap.height + 12 * 2,  // Height
            Bitmap.Config.ARGB_8888 // Config
        )
        val canvas = Canvas(dstBitmap)
        canvas.drawColor(Color.LTGRAY)
        canvas.drawBitmap(
            bitmap, // Bitmap
            20F, // Left
            20F, // Top
            null // Paint
        )
        customView.setImageBitmap(dstBitmap);
        d("gghh", curUri)
        //   drawingView = DrawingView(this)
//        drawView!!.addView(drawingView)
//drawingView.setImageBitmap(BitmapFactory.decodeFile(curUri))
        //customView.setImageBitmap(BitmapFactory.decodeFile(curUri));

         myImg = BitmapFactory.decodeFile(curUri)

        colorRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            colorSelectChanged(checkedId)
        }
        Rotate.setOnClickListener {
            // onEffectClicked(20)

            val matrix = Matrix()
            matrix.postRotate(90F)

            val rotated = Bitmap.createBitmap(
                myImg, 0, 0, myImg.width, myImg.height,
                matrix, true
            )

            customView.setImageBitmap(rotated)
            myImg = rotated
        }
        saveButtom.setOnClickListener {


            val bitmap = customView.drawable.toBitmap()

            var outStream: FileOutputStream? = null
            val sdCard: File = Environment.getExternalStorageDirectory()
            val dir = File(sdCard.getAbsolutePath().toString() + "/DeltaHackathonPhotoEditor")
            dir.mkdirs()
            val fileName =
                String.format("%d.jpg", System.currentTimeMillis())
            val outFile = File(dir, fileName)
            outStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            outStream.flush()
            outStream.close()
            Toast.makeText(this, "Image Saved Successfully 😊 ", Toast.LENGTH_LONG).show()

        }
        Crop.setOnClickListener {
            saveButton.visibility = View.VISIBLE
            closeButton.visibility = View.VISIBLE
            ViewClass.visibility = View.VISIBLE
            val cropClass = CropClass(this)
            ViewClass.removeAllViews()
            ViewClass.addView(cropClass)
        }
        saveButton.setOnClickListener {
            d("crop", "gg")
            val bitmap = customView.drawable.toBitmap()
            val cropImg = Bitmap.createBitmap(
                bitmap,
                left.toInt(),
                top.toInt(),
                right.toInt() - left.toInt(),
                bottom.toInt() - top.toInt()
            )
            customView.setImageBitmap(cropImg)
            myImg = cropImg
            ViewClass.visibility = View.GONE
            saveButton.visibility = View.INVISIBLE
            closeButton.visibility = View.INVISIBLE
        }
        saveButton2.setOnClickListener {
            val dstBitmap = Bitmap.createBitmap(
                bitmap.getWidth() ,  // Width
                bitmap.getHeight() ,  // Height
                Bitmap.Config.ARGB_8888 // Config
            )
            val canvas = Canvas(dstBitmap)
            canvas.drawBitmap(
                bitmap, // Bitmap
                0F, // Left
                0F, // Top
                null // Paint
            );
            for (pair in DrawingView.allPairs!!) {
                assert(pair.first != null)
                assert(pair.second != null)
                canvas.drawPath(pair.first!!, pair.second!!)
            }

            customView.setImageBitmap(dstBitmap)
            myImg = dstBitmap
            ViewClass.visibility = View.GONE
            saveButton2.visibility = View.INVISIBLE
            closeButton.visibility = View.INVISIBLE
        }
        saveButton4.setOnClickListener {
            val dstBitmap = Bitmap.createBitmap(
                bitmap.getWidth() ,  // Width
                bitmap.getHeight() ,  // Height
                Bitmap.Config.ARGB_8888 // Config
            )
            val canvas = Canvas(dstBitmap)
            canvas.drawBitmap(
                bitmap, // Bitmap
                0F, // Left
                0F, // Top
                null // Paint
            );
            for (pair in BlurView.allPairs!!) {
                assert(pair.first != null)
                assert(pair.second != null)
                canvas.drawPath(pair.first!!, pair.second!!)
            }

            customView.setImageBitmap(dstBitmap)
            myImg = dstBitmap
            ViewClass.visibility = View.GONE
            saveButton4.visibility = View.INVISIBLE
            closeButton.visibility = View.INVISIBLE
        }

        Doddle.setOnClickListener {
            saveButton2.visibility = View.VISIBLE
            closeButton.visibility = View.VISIBLE
            ViewClass.visibility = View.VISIBLE
            val drawingView = DrawingView(this)
            ViewClass.removeAllViews()
            ViewClass.addView(drawingView)
        }
        Blur.setOnClickListener {
            saveButton4.visibility = View.VISIBLE
            closeButton.visibility = View.VISIBLE
            ViewClass.visibility = View.VISIBLE
            val blurView = BlurView(this)
            ViewClass.removeAllViews()
            ViewClass.addView(blurView)

        }
    }

    private fun colorSelectChanged(selectedId: Int) {
        when (selectedId) {
            R.id.redButton -> {
                customView.setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            }
            R.id.yellowButton -> {
                customView.drawable.setColorFilter(Color.YELLOW, PorterDuff.Mode.MULTIPLY)
            }
            R.id.greenButton -> {
                customView.drawable.setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY)
            }
            R.id.blueButton -> {
                customView.drawable.setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY)
            }
            R.id.purpleButton -> {
                customView.drawable.setColorFilter(Color.CYAN, PorterDuff.Mode.MULTIPLY)
            }
        }
    }

    fun Share(view: View) {


        val path = MediaStore.Images.Media.insertImage(
            contentResolver,
            myImg,
            "Image Description",
            null
        )
        val uri = Uri.parse(path)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/jpeg"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

}