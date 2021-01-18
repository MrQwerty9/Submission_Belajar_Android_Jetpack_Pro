package com.sstudio.submissionbajetpackpro.ui.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.viewmodel.ViewModelFactory
import com.sstudio.submissionbajetpackpro.vo.Status
import kotlinx.android.synthetic.main.fragment_tvshow.*

class TvShowFragment : Fragment() {
    private lateinit var tvAdapter: TvAdapter
    private lateinit var viewModel: TvViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            tvAdapter = TvAdapter()
            val factory = ViewModelFactory.getInstance(requireActivity())
            viewModel = ViewModelProvider(this, factory)[TvViewModel::class.java]
            observeData()
            swipe_layout.setOnRefreshListener {
                viewModel.fetchListMovie()
                observeData()
                swipe_layout.isRefreshing = false
            }
            with(rv_list_tv_show) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
//                adapter = tvAdapter
            }
            tvAdapter.onItemClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_TV)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
                startActivity(intent)
            }
        }
    }

    private fun observeData() {
        viewModel.listTvShow?.observe(viewLifecycleOwner, { resource ->
            if (resource != null) {
                when (resource.status) {
                    Status.LOADING -> progress_bar.visibility = View.VISIBLE
                    Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        tvAdapter.submitList(resource.data)
                        rv_list_tv_show.adapter  = tvAdapter
                    }
                    Status.ERROR -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
