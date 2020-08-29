package com.mrspd.photoeditor.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.mrspd.photoeditor.PhotoEditor

class StickerClass : View {

    var b = 0f
    var h = 0f

    constructor(context: Context?) : super(context) {
        init(null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
        init(attrs)
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(attrs)
    }

    private fun init(set: AttributeSet?) {
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint =
            Paint(Paint.ANTI_ALIAS_FLAG)
        val paint1 =
            Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.parseColor("#44000000")
        paint1.color = Color.parseColor("#DCDCDC")
        canvas.drawRect(
            PhotoEditor.left,
            PhotoEditor.top,
            PhotoEditor.right,
            PhotoEditor.bottom,
            paint
        )
        canvas.drawCircle(PhotoEditor.left, PhotoEditor.top, 30f, paint1)
        canvas.drawCircle(PhotoEditor.left, PhotoEditor.bottom, 30f, paint1)
        canvas.drawCircle(
            PhotoEditor.left,
            PhotoEditor.top + (PhotoEditor.bottom - PhotoEditor.top) / 2,
            30f,
            paint1
        )
        canvas.drawCircle(PhotoEditor.right, PhotoEditor.top, 30f, paint1)
        canvas.drawCircle(PhotoEditor.right, PhotoEditor.bottom, 30f, paint1)
        canvas.drawCircle(
            PhotoEditor.right,
            PhotoEditor.top + (PhotoEditor.bottom - PhotoEditor.top) / 2,
            30f,
            paint1
        )
        canvas.drawCircle(
            PhotoEditor.left + (PhotoEditor.right - PhotoEditor.left) / 2,
            PhotoEditor.top,
            30f,
            paint1
        )
        canvas.drawCircle(
            PhotoEditor.left + (PhotoEditor.right - PhotoEditor.left) / 2,
            PhotoEditor.bottom,
            30f,
            paint1
        )
    }

    var dx = 0.0
    var dy = 0.0
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val value = super.onTouchEvent(event)
        Log.w(TAG, "QWE")
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                val x = event.x
                val y = event.y
                Log.w(
                    TAG,
                    "QWE 1 $x $y"
                )
                if (x > PhotoEditor.left + 30 && x < PhotoEditor.right - 30 && y > PhotoEditor.top + 30 && y < PhotoEditor.bottom - 30) {
                    b = (PhotoEditor.right - PhotoEditor.left) / 2
                    h = (PhotoEditor.bottom - PhotoEditor.top) / 2
                    PhotoEditor.left = x - b
                    PhotoEditor.right = x + b
                    PhotoEditor.top = y - h
                    PhotoEditor.bottom = y + h
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.left - 30 && x < PhotoEditor.left + 30 && y < PhotoEditor.top + (PhotoEditor.bottom - PhotoEditor.top) / 2 + 30 && y > PhotoEditor.top + (PhotoEditor.bottom - PhotoEditor.top) / 2 - 30) {
                    PhotoEditor.left = x
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.right - 30 && x < PhotoEditor.right + 30 && y < PhotoEditor.top + (PhotoEditor.bottom - PhotoEditor.top) / 2 + 30 && y > PhotoEditor.top + (PhotoEditor.bottom - PhotoEditor.top) / 2 - 30) {
                    PhotoEditor.right = x
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.left + (PhotoEditor.right - PhotoEditor.left) / 2 - 30 && x < PhotoEditor.left + (PhotoEditor.right - PhotoEditor.left) / 2 + 30 && y < PhotoEditor.bottom + 30 && y > PhotoEditor.bottom - 30) {
                    PhotoEditor.bottom = y
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.left + (PhotoEditor.right - PhotoEditor.left) / 2 - 30 && x < PhotoEditor.left + (PhotoEditor.right - PhotoEditor.left) / 2 + 30 && y < PhotoEditor.top + 30 && y > PhotoEditor.top - 30) {
                    PhotoEditor.top = y
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.left - 30 && x < PhotoEditor.left + 30 && y < PhotoEditor.top + 30 && y > PhotoEditor.top - 30) {
                    PhotoEditor.left = x
                    PhotoEditor.top = y
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.left - 30 && x < PhotoEditor.left + 30 && y < PhotoEditor.bottom + 30 && y > PhotoEditor.bottom - 30) {
                    PhotoEditor.left = x
                    PhotoEditor.bottom = y
                    postInvalidate()
                    return true
                } else if (x > PhotoEditor.right - 30 && x < PhotoEditor.right + 30 && y < PhotoEditor.bottom + 30 && y > PhotoEditor.bottom - 30) {
                    PhotoEditor.right = x
                    PhotoEditor.bottom = y
                    postInvalidate()
                    return true
                } else if (y < PhotoEditor.top + 30 && y > PhotoEditor.top - 30 && x > PhotoEditor.right - 30 && x < PhotoEditor.right + 30) {
                    PhotoEditor.right = x
                    PhotoEditor.top = y
                    postInvalidate()
                    return true
                }
                return value
            }
        }
        return value
    }

    companion object {
        private const val TAG = "QWERTYUI"
    }
}