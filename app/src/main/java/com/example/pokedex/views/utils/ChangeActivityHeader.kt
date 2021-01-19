package com.example.pokedex.views.utils

interface ChangeActivityHeader {

    fun setToolbar(string: String?)

    fun enableBack()

    fun disableBack()

    fun startAnimation()

    fun stopAnimation()
}