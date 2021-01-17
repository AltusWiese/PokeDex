package com.example.pokedex.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokedex.R


class SplashscreenActivity : AppCompatActivity() {

    private lateinit var welcomeThread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pokedex_splashscreen)

        delayLoading()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun delayLoading() {

         welcomeThread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(3000)
                } catch (e: Exception) {
                } finally {
                    val i = Intent(
                        this@SplashscreenActivity,
                        MainActivity::class.java
                    )
                    startActivity(i)
                    finish()
                }
            }
        }
        welcomeThread.start()
    }
}