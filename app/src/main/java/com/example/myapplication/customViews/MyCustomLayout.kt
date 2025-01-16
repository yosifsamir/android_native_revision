package com.example.myapplication.customViews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class MyCustomLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Measure the children with the given measure specs
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        var totalWidth = 0
        var totalHeight = 0

        // Measure the children and calculate the total required size
        for (i in 0 until childCount) {
            val child = getChildAt(i)

            // Measure each child with the parent's measure specs
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            // Get the measured width and height of the child
            totalWidth = Math.max(totalWidth, child.measuredWidth)
            totalHeight += child.measuredHeight
        }

        // For wrap_content behavior, return the required size based on the children
        val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize // Parent width is fixed
        } else {
            totalWidth // Wrap content based on children's total width
        }

        val measuredHeight = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize // Parent height is fixed
        } else {
            totalHeight // Wrap content based on children's total height
        }

        // Set the measured width and height for the parent view
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // position children
        var left = 0
        var top = 0
        children.forEachIndexed { index, view ->
            view.layout(left, top, left + view.measuredWidth, top + view.measuredHeight)
            left += view.measuredWidth
            top += view.measuredHeight
        }
    }

    // No need to override onDraw because the framework will call onDraw on all children .
}