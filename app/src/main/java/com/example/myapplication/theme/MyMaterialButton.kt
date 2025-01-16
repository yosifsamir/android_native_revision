package com.example.myapplication.theme

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import com.google.android.material.button.MaterialButton

class MyMaterialButton : MaterialButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        background= ColorDrawable(0xFFFFFF00.toInt()) // yellow
        backgroundTintList = null
        setAllCaps(false)
        setPadding(0,0,0,0)
        hint = "Hint"
        textSize = 20f
        setTextColor(0xFF00FF00.toInt())
        setTypeface(null, Typeface.BOLD)

    }

}