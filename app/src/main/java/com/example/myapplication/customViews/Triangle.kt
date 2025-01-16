package com.example.myapplication.customViews

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class Triangle(
    private var point1: Point,
    private var point2: Point,
    private var hypotenusePoint: Point
) {

    fun area() = 0.5f * hypotenusePoint.x * hypotenusePoint.y

    fun perimeter() = hypotenusePoint.length() + point1.length() + point2.length()

    fun isRightTriangle() =
        hypotenusePoint.x * hypotenusePoint.x + hypotenusePoint.y * hypotenusePoint.y == point1.x * point1.x + point1.y * point1.y

    fun isScaleneTriangle() =
        hypotenusePoint.x * hypotenusePoint.x + hypotenusePoint.y * hypotenusePoint.y != point1.x * point1.x + point1.y * point1.y

    fun isIsoscelesTriangle() =
        hypotenusePoint.x * hypotenusePoint.x + hypotenusePoint.y * hypotenusePoint.y == point1.x * point1.x + point1.y * point1.y

    fun isEquilateralTriangle() = hypotenusePoint.x == point1.x && hypotenusePoint.y == point1.y

    fun isTriangle() =
        hypotenusePoint.x * hypotenusePoint.x + hypotenusePoint.y * hypotenusePoint.y == point1.x * point1.x + point1.y * point1.y


    fun rotateTriangle(angleDegrees: Float) {
        // Convert angle to radians
        val angleRadians = Math.toRadians(angleDegrees.toDouble()).toFloat()

        // Calculate the centroid of the triangle
        val centroidX = (point1.x + point2.x + hypotenusePoint.x) / 3
        val centroidY = (point1.y + point2.y + hypotenusePoint.y) / 3

        // Function to rotate a point around the centroid
        fun rotatePoint(x: Float, y: Float): Point {
            val dx = x - centroidX
            val dy = y - centroidY

            val rotatedX = centroidX + (dx * Math.cos(angleRadians.toDouble()) - dy * Math.sin(angleRadians.toDouble())).toFloat()
            val rotatedY = centroidY + (dx * Math.sin(angleRadians.toDouble()) + dy * Math.cos(angleRadians.toDouble())).toFloat()

            return Point(rotatedX, rotatedY)
        }

        // Apply rotation to each point of the triangle
        point1 = rotatePoint(point1.x, point1.y)
        point2 = rotatePoint(point2.x, point2.y)
        hypotenusePoint = rotatePoint(hypotenusePoint.x, hypotenusePoint.y)
    }


    fun scaleTriangle(factor: Float) {
        val centroidX = (point1.x + point2.x + hypotenusePoint.x) / 3
        val centroidY = (point1.y + point2.y + hypotenusePoint.y) / 3

        fun scalePoint(x: Float, y: Float): Point {
            val dx = x - centroidX
            val dy = y - centroidY

            val scaledX = centroidX + dx * factor
            val scaledY = centroidY + dy * factor

            return Point(scaledX, scaledY)
        }

        point1 = scalePoint(point1.x, point1.y)
        point2 = scalePoint(point2.x, point2.y)
        hypotenusePoint = scalePoint(hypotenusePoint.x, hypotenusePoint.y)
    }

    fun draw(canvas: Canvas) {
        canvas.drawLine(point1.x, point1.y, point2.x, point2.y, Paint().apply {
            color = Color.WHITE
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        canvas.drawLine(point2.x, point2.y, hypotenusePoint.x, hypotenusePoint.y, Paint().apply {
            color = Color.BLUE
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
        canvas.drawLine(hypotenusePoint.x, hypotenusePoint.y, point1.x, point1.y, Paint().apply {
            color = Color.YELLOW
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
    }





    fun animateRotation(canvas: Canvas) {
        val animator = ValueAnimator.ofFloat(0f, 360f)
        animator.duration = 2000 // 2 seconds for a full rotation
        animator.repeatCount = ValueAnimator.INFINITE
        animator.addUpdateListener { animation ->
            val angle = animation.animatedValue as Float
            rotateTriangle(angle)
            draw(canvas) // Draw the rotated triangle
        }
        animator.start()
    }
}

class Point(val x: Float, val y: Float) {

    fun length() = sqrt((x * x + y * y).toDouble())
}

class Pentagon(
    private val center: Point,
    private val sideLength: Float
) {

    fun area(): Float {
        return (5 * sideLength * sideLength) / (4 * Math.tan(Math.PI / 5).toFloat())
    }

    fun perimeter(): Float {
        return 5 * sideLength
    }

    fun draw(canvas: Canvas) {
        val path = Path()

        for (i in 0..4) {
            val x = (center.x + sideLength * cos(i * 2 * Math.PI / 5)).toFloat()
            val y = (center.y + sideLength * sin(i * 2 * Math.PI / 5)).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        path.close()
        canvas.drawPath(path, Paint().apply {
            color = Color.WHITE
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        })
    }
}


class Derivative(private val function: (Double) -> Double) {

    // Compute the derivative using the definition of the derivative (numerical approximation)
    fun computeDerivativeAtPoint(x: Double, h: Double = 1e-5): Double {
        return (function(x + h) - function(x)) / h
    }

    fun draw(canvas: Canvas, startX: Float, endX: Float, step: Float = 1f) {
        val paint = Paint().apply {
            color = Color.BLUE
            strokeWidth = 5f
            style = Paint.Style.STROKE
            isAntiAlias = true
        }

        // Plot the function f(x)
        for (x in startX.toInt() until endX.toInt() step step.toInt()) {
            val x1 = x.toFloat()
            val y1 = function(x1.toDouble()).toFloat()
            val x2 = (x + step).toFloat()
            val y2 = function(x2.toDouble()).toFloat()

            canvas.drawLine(x1, y1, x2, y2, paint)
        }

        // Draw the derivative at a specific point (tangent line)
        val pointX = (startX + endX) / 2  // Example: draw the tangent line at the midpoint
        val pointY = function(pointX.toDouble()).toFloat()
        val derivativeValue = computeDerivativeAtPoint(pointX.toDouble())

        val tangentY = pointY - derivativeValue * pointX

        // Draw tangent line (as an approximation)
        val xStart = startX
        val yStart = derivativeValue * xStart + tangentY
        val xEnd = endX
        val yEnd = derivativeValue * xEnd + tangentY

        canvas.drawLine(xStart, yStart.toFloat(), xEnd, yEnd.toFloat(), Paint().apply {
            color = Color.RED
            strokeWidth = 5f
            isAntiAlias = true
        })
    }
}

class Integral(private val function: (Double) -> Double) {

    // Compute the definite integral using the trapezoidal rule
    fun computeIntegral(start: Double, end: Double, n: Int = 1000): Double {
        val h = (end - start) / n
        var integralValue = 0.5 * (function(start) + function(end))
        for (i in 1 until n) {
            integralValue += function(start + i * h)
        }
        return integralValue * h
    }

    // Visualize the integral (area under the curve)
    fun draw(canvas: Canvas, startX: Float, endX: Float, step: Float = 1f) {
        val paint = Paint().apply {
            color = Color.GREEN
            strokeWidth = 5f
            style = Paint.Style.FILL
            isAntiAlias = true
        }

        // Plot the area under the curve (integral)
        for (x in startX.toInt() until endX.toInt() step step.toInt()) {
            val x1 = x.toFloat()
            val y1 = function(x1.toDouble()).toFloat()
            val x2 = (x + step).toFloat()
            val y2 = function(x2.toDouble()).toFloat()

            // Draw small trapezoidal area under the curve
            canvas.drawRect(x1, 0f, x2, y1, paint)
        }
    }
}