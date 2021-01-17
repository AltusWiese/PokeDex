package com.example.pokedex.views.utils

import android.view.View
import com.airbnb.lottie.LottieAnimationView

class AnimationView {
    private var animationView: LottieAnimationView? = null
    fun startAnimationView(lottieAnimationView: LottieAnimationView?) {
        animationView = lottieAnimationView
        animationView!!.visibility = View.VISIBLE
        animationView!!.playAnimation()
    }

    fun stopAnimationView(lottieAnimationView: LottieAnimationView) {
        animationView = lottieAnimationView
        animationView!!.visibility = View.INVISIBLE
        lottieAnimationView.cancelAnimation()
    }
}