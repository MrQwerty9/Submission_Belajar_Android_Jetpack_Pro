package com.sstudio.submissionbajetpackpro.ui.movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sstudio.submissionbajetpackpro.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment = MovieFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}