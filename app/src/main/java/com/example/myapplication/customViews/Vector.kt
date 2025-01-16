package com.example.myapplication.customViews

import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

// This class will be 2 Vector .
class Vector(public val x: Float, public val y: Float) {

    fun length() = sqrt((x * x + y * y).toDouble())// Pythagorean theorem
    fun normalize() = Vector((x / length()).toFloat(), (y / length()).toFloat())

    fun dot(other: Vector) = x * other.x + y * other.y
    fun crossProduct(other: Vector) = x * other.y - y * other.x

    fun add(other: Vector) = Vector(x + other.x, y + other.y)

    fun sub(other: Vector) = Vector(x - other.x, y - other.y)

    fun mul(other: Vector) = Vector(x * other.x, y * other.y)

    fun div(other: Vector) = Vector(x / other.x, y / other.y)
    fun divByScalar(scalar: Float) = Vector(x / scalar, y / scalar)

    fun scale(scale: Float) = Vector(x * scale, y * scale)

    fun rotate(angle: Float) =
        Vector(x * cos(angle) - y * sin(angle), x * sin(angle) + y * cos(angle))

    fun rotate(angle: Float, pivot: Vector) = Vector(
        (x - pivot.x) * cos(angle) - (y - pivot.y) * sin(angle) + pivot.x,
        (x - pivot.x) * sin(angle) + (y - pivot.y) * cos(angle) + pivot.y
    )

    fun limit(max: Float) = if (length() > max) normalize().scale(max) else this

    // Angle between two vectors in radians
    fun angleBetween(other: Vector): Double {
        // Use the dot product formula to find the cosine of the angle
        val cosTheta = this.dot(other) / (this.length() * other.length())
        // Clamp cosTheta between -1 and 1 to prevent rounding errors
        return acos(cosTheta)
    }

    // Sine of the angle between two vectors (using cross product)
    fun sinAngleBetween(other: Vector): Double {
        val crossProd = this.crossProduct(other)
        val sinTheta = crossProd / (this.length() * other.length())
        return sinTheta
    }

}

fun main(args: Array<String>) {
    val vector1 = Vector(0f, 4f)
    val vector2 = Vector(5f, 6f)

    println("Vector1: (${vector1.x}, ${vector1.y})")
    println("Vector2: (${vector2.x}, ${vector2.y})")
    println("Length of vector1: ${vector1.length()}")
    println("Length of vector2: ${vector2.length()}")

    val normalizedVector1 = vector1.normalize()
    val normalizedVector2 = vector2.normalize()
    println("Normalized vector1: (${normalizedVector1.x}, ${normalizedVector1.y})")
    println("Normalized vector2: (${normalizedVector2.x}, ${normalizedVector2.y})")

    val dotProduct = vector1.dot(vector2)
    println("Dot product of vector1 and vector2: $dotProduct")

    val crossProduct = vector1.crossProduct(vector2)
    println("Cross product of vector1 and vector2: $crossProduct")

    val vector3 = vector1.add(vector2)
    println("Adding Two Vectors:- Vector3: (${vector3.x}, ${vector3.y})")

    val vector4 = vector1.sub(vector2)
    println("Subtracting Two Vectors:- Vector4: (${vector4.x}, ${vector4.y})")

    val vector5 = vector1.mul(vector2)
    println("Multiplying Two Vectors:- Vector5: (${vector5.x}, ${vector5.y})")

    val vector6 = vector1.div(vector2)
    println("Dividing Two Vectors:- Vector6: (${vector6.x}, ${vector6.y})")

    val vector7 = vector1.scale(2f)
    println("Scaling Vector1 by 2: Vector7: (${vector7.x}, ${vector7.y})")

    val vector8 = vector1.rotate(90f)
    println("Rotating Vector1 by 90 degrees: Vector8: (${vector8.x}, ${vector8.y})")

    val vector9 = vector1.rotate(90f, vector2)
    println("Rotating Vector1 by 90 degrees around Vector2: Vector9: (${vector9.x}, ${vector9.y})")

    val vector10 = vector1.limit(5f)
    println("Limiting Vector1 to length 5: Vector10: (${vector10.x}, ${vector10.y})")

    val angle = vector1.angleBetween(vector2)
    val sinAngle = vector1.sinAngleBetween(vector2)
    println("Angle between vector1 and vector2: $angle")
    println("Sine of the angle between vector1 and vector2: $sinAngle")
}