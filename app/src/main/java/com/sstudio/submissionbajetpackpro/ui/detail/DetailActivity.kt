package com.sstudio.submissionbajetpackpro.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sstudio.submissionbajetpackpro.R

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}
