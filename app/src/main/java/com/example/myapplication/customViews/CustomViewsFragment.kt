package com.example.myapplication.customViews

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentCustomViewsBinding

class CustomViewsFragment : BaseFragment<FragmentCustomViewsBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCustomViewsBinding = FragmentCustomViewsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.colorFilterBtn.setOnClickListener {
            val colorMatrix = ColorMatrix()
            colorMatrix.setSaturation(0f)
            colorMatrix.set(
                floatArrayOf(
                    0f, // I cancelled "Red" color .
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    1f,
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    1f,
                    0f,
                    0f,
                    0f,
                    0f,
                    0f,
                    1f,
                    0f
                )
            )
            binding.apply {
                // it do invalidate() internally. maybe this colorFilter talk to Shader in the GPU .
                imageFilterImg.colorFilter = ColorMatrixColorFilter(colorMatrix)
            }

        }
    }
}