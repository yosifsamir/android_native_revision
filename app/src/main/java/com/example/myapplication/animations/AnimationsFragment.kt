package com.example.myapplication.animations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import com.example.myapplication.base.BaseFragment
import com.example.myapplication.databinding.FragmentAnimationsBinding

class AnimationsFragment : BaseFragment<FragmentAnimationsBinding>() {

    override fun inflateBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentAnimationsBinding = FragmentAnimationsBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            scaleAnimationBtn.setOnClickListener {
                val scaleAnimation = ScaleAnimation(
                    1f, 2f,  // Start scaling from 1x to 2x (instead of 0f to 2f)
                    1f, 2f,  // Similarly, scale vertically from 1x to 2x
                    Animation.RELATIVE_TO_SELF, 0.5f,  // Pivot the scale at the center of the view
                    Animation.RELATIVE_TO_SELF, 0.5f)
                scaleAnimation.duration = 2000
                scaleAnimation.repeatCount = 10
                scaleAnimation.repeatMode = ScaleAnimation.REVERSE
                scaleAnimation.fillAfter = true
//                scaleAnimation.start()

                scaleLabelTxt.startAnimation(scaleAnimation)

            }
            opacityAnimationBtn.setOnClickListener {
                // use AnimationSet to chain animations
                val opacityAnimation = AlphaAnimation(0.0f, 1.0f)
                opacityAnimation.duration = 2000
                opacityAnimation.repeatCount = 10
                opacityAnimation.repeatMode = Animation.REVERSE
                opacityAnimation.fillAfter = true
                scaleLabelTxt.startAnimation(opacityAnimation)
            }
        }

    }
}