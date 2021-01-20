package com.sstudio.submissionbajetpackpro.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sstudio.submissionbajetpackpro.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = this.getString(R.string.app_name)
        val navController = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottom_nav,
            navController.navController
        )
    }
}