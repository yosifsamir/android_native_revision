package com.example.myapplication.surfaceView

import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.SurfaceHolder
import android.view.SurfaceHolder.Callback
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentSurfaceViewBinding

class SurfaceViewFragment : BaseFragment<FragmentSurfaceViewBinding>() {
    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSurfaceViewBinding = FragmentSurfaceViewBinding.inflate(inflater, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.surfaceView.setZOrderOnTop(true)
        binding.surfaceView.background = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            binding.surfaceView.backgroundTintBlendMode = BlendMode.SRC_IN
        }
        binding.surfaceView.holder?.setFormat(PixelFormat.TRANSLUCENT)
        binding.surfaceView.holder?.addCallback(object : Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                Log.d("DDDDD", "surfaceCreated is called")

            }

            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                Log.d("DDDDD", "surfaceChanged is called")
                val canvas = holder.lockCanvas()
                Log.d("DDDDD", "canvas is called ${canvas}")
                canvas?.drawColor(Color.RED)
                holder.unlockCanvasAndPost(canvas)
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Log.d("DDDDD", "surfaceDestroyed is called")

            }
        })
    }


}