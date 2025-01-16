package com.example.myapplication.surfaceView

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PixelFormat
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.math.cos
import kotlin.math.sin

class MyCustomSurfaceView : SurfaceView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    var thread : Thread? = null
    init {
        setZOrderOnTop(true)
        holder.setFormat(PixelFormat.TRANSLUCENT)
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
//                Thread {
//                    while(true){
//                        val canvas = holder.lockCanvas()
//                        Thread.sleep(2000)
//                        canvas?.drawColor(Color.rgb((0..255).random(), (0..255).random(), (0..255).random()))
//                        holder.unlockCanvasAndPost(canvas)
//                    }
//                }.start()


                thread = Thread {
                    var cx : Float = width / 2.0f
                    var cy : Float = height / 2.0f
                    var radius = cx - 40

                    val circlePaint = Paint()
                    circlePaint.color = Color.BLUE
                    circlePaint.style = Paint.Style.STROKE
                    circlePaint.strokeWidth = 5f
                    circlePaint.isAntiAlias = true

                    while (true) {
                        // we want to rotate a circle around radius .
                        for (i in 0..360) {
                            val circleCanvas = holder.lockCanvas()
                            if(circleCanvas != null){
                                circleCanvas.drawColor(Color.GREEN)
                                circleCanvas.drawCircle(
                                    (radius * cos(i * Math.PI / 180) + cx ).toFloat(),
                                    (radius * sin(i * Math.PI / 180) + cy ).toFloat(),
                                    20f,
                                    circlePaint
                                )
                                holder.unlockCanvasAndPost(circleCanvas)
                            }
                            try {
                                Thread.sleep(2)
                            } catch (_: IllegalStateException) { }
                        }
                    }

                }

                thread?.start()

            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {}
        })
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        thread = null
    }

}