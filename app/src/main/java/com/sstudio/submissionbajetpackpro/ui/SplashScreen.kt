package com.sstudio.submissionbajetpackpro.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.ui.movie.MainActivity

class SplashScreen : AppCompatActivity() {
    private val delayMillis: Long = 2000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        Handler().postDelayed({
            val intent = Intent(this@SplashScreen, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, delayMillis)
    }
}