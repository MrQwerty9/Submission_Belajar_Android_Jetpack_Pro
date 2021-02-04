package com.sstudio.submissionbajetpackpro.ui.tv.list

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.ui.tv.TvAdapter
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.movie.list.MovieListActivity
import kotlinx.android.synthetic.main.activity_tv_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class TvListActivity : AppCompatActivity() {

    companion object{
        val PARAMS_EXTRA = "paramsExtra"
    }

    private val viewModel: TvListViewModel by viewModel()
    private lateinit var tvAdapter: TvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_list)

        val params = intent.getParcelableExtra<Params.MovieParams>(MovieListActivity.PARAMS_EXTRA)
        viewModel.getTv(params)
        observeData()
        swipe_layout.setOnRefreshListener {
            viewModel.getTv(params)
            observeData()
            swipe_layout.isRefreshing = false
        }

        tvAdapter.onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
            startActivity(intent)
        }
        with(rv_list_tv_show) {
            layoutManager = LinearLayoutManager(context)
//                setHasFixedSize(true)
            adapter = tvAdapter
        }

        viewModel.state.observe(this, {
            when (it.status) {
                NetworkState.Status.LOADING -> {
                    progress_bar.visibility = View.VISIBLE
                    Log.d("mytag", "state loading")
                }
                NetworkState.Status.SUCCESS -> {
                    progress_bar.visibility = View.GONE
                    Log.d("mytag", "state success")
                }
                NetworkState.Status.EMPTY -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, "Tidak ada data ${it.msg}", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    progress_bar.visibility = View.GONE
                    Toast.makeText(this, "Terjadi Kesalahan ${it.msg}", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeData() {
        tvAdapter = TvAdapter()
        viewModel.listTv?.observe(this) {
            tvAdapter.submitList(it)
        }
    }
}