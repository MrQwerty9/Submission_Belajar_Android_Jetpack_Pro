package com.sstudio.submissionbajetpackpro.ui.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.ui.tv.TvAdapter
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.koin.android.viewmodel.ext.android.viewModel

class TvShowFragment : Fragment() {
    private lateinit var tvAdapter: TvAdapter
    private val viewModel: TvViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            tvAdapter = TvAdapter()
            observeData()
            swipe_layout.setOnRefreshListener {
                viewModel.fetchListMovie()
                observeData()
                swipe_layout.isRefreshing = false
            }
            with(rv_list_tv_show) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
            tvAdapter.onItemClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_TV)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
                startActivity(intent)
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
                when (resource) {
                    is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        progress_bar.visibility = View.GONE
                        tvAdapter.submitList(resource.data)
                        rv_list_tv_show.adapter  = tvAdapter
                    }
                    is Resource.Error -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
