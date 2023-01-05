package com.example.paintapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.paintapp.MainActivity.Companion.paintBrush
import com.example.paintapp.MainActivity.Companion.path

class PaintView : View {

    companion object {

        var params : ViewGroup.LayoutParams?= null

        //this will store all the paths that we draw on the screen
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()

        //this sets the first color to black
        var currentBrush = Color.BLACK
    }

    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    //method that will initialize the path
    private fun init() {
        paintBrush.isAntiAlias = true //will make the brush stroke smooth
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

        //initialize the params attribute
        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    //this method will register the moment user draws on the canvass
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x,y)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else -> return false
        }
        //this will show users that something happened
        //necessary on apps
        postInvalidate()
        return false;
    }

    //this method is for when user draw something
    override fun onDraw(canvas: Canvas) {
        //we will take the path and draw it 1 by 1 on the screen
        for (i in pathList.indices) {
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)
            invalidate()
        }
    }

}