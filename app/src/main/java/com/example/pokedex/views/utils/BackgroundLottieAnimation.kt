package com.example.pokedex.views.utils

import android.view.View
import com.airbnb.lottie.LottieAnimationView

interface BackgroundLottieAnimation {

    fun startBackgroundAnimation(animationView: LottieAnimationView?): AnimationView {
        val anim = AnimationView()
        anim.startAnimationView(animationView)
        return anim
    }

    fun stopBackgroundAnimation(view: AnimationView, animationView: LottieAnimationView) {
        view.stopAnimationView(animationView)
        animationView.visibility = View.GONE
    }
}