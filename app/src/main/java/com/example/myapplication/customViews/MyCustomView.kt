package com.example.myapplication.customViews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MyCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var vector1 = Vector(-50f, -50f)
    private var vector2 = Vector(50f, 100f)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(width / 2f, height / 2f)
        canvas.scale(1f, -1f) // very important fillip the y-axis .

        val xAxisColor = Color.GREEN //
        val yAxisColor = Color.RED //

        canvas.drawLine(0f, 0f, 0f, height / 2f, Paint().apply {
            color = yAxisColor
            strokeWidth = 10f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        canvas.drawLine(0f, 0f, width / 2f, 0f, Paint().apply {
            color = xAxisColor
            strokeWidth = 10f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })

        drawBasicLines(canvas)



        canvas.restore()
    }

    private fun drawBasicLines(canvas: Canvas) {
        val vector1Color = Color.BLUE // Vector 1
        val vector2Color = Color.YELLOW // Vector 2
        val addVectorColor = Color.MAGENTA // add Vector
        val subtractVectorColor = Color.WHITE // subtract Vector
        val divVectorColor = Color.CYAN // mul Vector

        canvas.drawLine(0f, 0f, vector1.x, vector1.y, Paint().apply {
            color = vector1Color
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        canvas.drawLine(0f, 0f, vector2.x, vector2.y, Paint().apply {
            color = vector2Color
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        // I want to draw add product
        val vector3 = vector1.add(vector2)
        canvas.drawLine(0f, 0f, vector3.x, vector3.y, Paint().apply {
            color = addVectorColor
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        ////////////////////// Subtraction ////////////////////
        // I want to draw sub product
        val vector4 = vector1.sub(vector2)
        canvas.drawLine(0f, 0f, vector4.x, vector4.y, Paint().apply {
            color = subtractVectorColor
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
            canvas.drawLine(vector2.x, vector2.y, vector2.x + vector4.x, vector2.y+vector4.y, Paint().apply {
            color = subtractVectorColor
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        //////////////////////////////////////////////////////////


//        // I want to draw mul product
//        val vector5 = vector1.mul(vector2)
//        canvas.drawLine(0f, 0f, vector5.x, vector5.y, Paint().apply {
//            color = mulVectorColor
//            strokeWidth = 5f
//            style = Paint.Style.STROKE
//            isAntiAlias = true
//        })
//
//        // I want to draw div product
        val vector6 = vector1.divByScalar(2f)
        canvas.drawLine(0f, 0f, vector6.x, vector6.y, Paint().apply {
            color = divVectorColor
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
//
//        // I want to draw dot product
        val vector7 = vector1.dot(vector2)
        canvas.drawLine(0f, 0f, vector7, 0f, Paint().apply {
            color = Color.rgb(255, 25, 0)
            strokeWidth = 3.5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
//
//        // I want to draw cross product
        val vector8 = vector1.crossProduct(vector2)
        canvas.drawLine(0f, 0f, 0f, vector8, Paint().apply {
            color = 0xff00ffff.toInt()
            strokeWidth = 3.5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })


        // I want to draw angle
        canvas.save()

        canvas.scale(1f, -1f)
        canvas.translate(-(width / 2f), -(height / 2f))
        val angle = vector1.angleBetween(vector2)
        canvas.drawText("Angle: ${(angle * 180 / Math.PI).toInt()}", 20f, 20f, Paint().apply {
            color = 0xffFF00FF.toInt()
            strokeWidth = 1f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        canvas.restore()



        //
//        // I want to draw limit
//        val vector9 = vector1.limit(100f)
//        canvas.drawLine(0f, 0f, vector9.x, vector9.y, Paint().apply {
//            color = 0xff000000.toInt()
//            strokeWidth = 5f
//            style = Paint.Style.STROKE
//            isAntiAlias = true
//        })
//
//        // I want to draw rotate
//        val vector10 = vector1.rotate(90f, Vector(0f, 0f))
//        canvas.drawLine(0f, 0f, vector10.x, vector10.y, Paint().apply {
//            color = 0xff000000.toInt()
//            strokeWidth = 5f
//            style = Paint.Style.STROKE
//            isAntiAlias = true
//        })
//

//        for(i in 0..360){
//            var cosX = cos(i * (Math.PI/180)).toFloat()
//            cosX *= 200f
//            canvas.drawLine(0f, 0f, cosX, 0f, Paint().apply {
//                color = 0xffFF0000.toInt()
//                strokeWidth = 5f
//                style = Paint.Style.STROKE
//                isAntiAlias = true
//            })
//        }

    }
}