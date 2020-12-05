package com.sstudio.submissionbajetpackpro.ui.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvShowFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            val viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[TvViewModel::class.java]
            val movies = viewModel.getTv()

            val tvAdapter = TvAdapter()
            tvAdapter.setTv(movies)

            rv_list_tv_show.layoutManager = LinearLayoutManager(context)
            rv_list_tv_show.setHasFixedSize(true)
            rv_list_tv_show.adapter = tvAdapter
        }
    }
}
