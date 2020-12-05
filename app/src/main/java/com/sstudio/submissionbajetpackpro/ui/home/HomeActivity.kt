package com.sstudio.submissionbajetpackpro.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sstudio.submissionbajetpackpro.R
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        pager_container.adapter = sectionsPagerAdapter
        tab_layout.setupWithViewPager(pager_container)
        supportActionBar?.elevation = 0f
    }
}