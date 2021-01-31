package com.sstudio.submissionbajetpackpro.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.domain.model.Movie
import com.sstudio.submissionbajetpackpro.core.ui.movie.MovieAdapter
import com.sstudio.submissionbajetpackpro.core.vo.NetworkState
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModel()
    private var pagedListMovie: PagedList<Movie>? = null
    private var networkState: NetworkState? = null
    private var localToNetwork = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            setHasOptionsMenu(true)
//            observeData()
            movieAdapter = MovieAdapter()
            swipe_layout.setOnRefreshListener {
                viewModel.fetchListMovie()
//                observeData()
                swipe_layout.isRefreshing = false
            }

            movieAdapter.onItemClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
                startActivity(intent)
            }
            adapterManage()

//            CombinedLiveData.(viewModel.state, viewModel.listMovie)
            viewModel.state.observe(viewLifecycleOwner, {
                networkState = it
                when (it.status) {
                    NetworkState.Status.LOADING -> {
                        progress_bar.visibility = View.VISIBLE
                    }
                    NetworkState.Status.SUCCESS -> {
                        progress_bar.visibility = View.GONE
                        moviePopulate()
                        localToNetwork = false
                    }
                    NetworkState.Status.EMPTY -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Tidak ada data ${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan ${it.msg}", Toast.LENGTH_SHORT).show()
                    }
                }
            })

            viewModel.listMovie?.observe(viewLifecycleOwner) {
                pagedListMovie = it
                moviePopulate()
            }
        }
    }

    private fun moviePopulate(){
        Log.d("mytag", "populatemovie $pagedListMovie")
        if (!pagedListMovie.isNullOrEmpty()) {
            if (networkState == NetworkState.SUCCESS && localToNetwork)
                adapterManage()
            movieAdapter.submitList(pagedListMovie)
        }
    }

    private fun adapterManage(){
        movieAdapter = MovieAdapter()
        with(rv_list_movie) {
            layoutManager = LinearLayoutManager(context)
//                setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_button_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.search -> {
                startActivity(Intent(context, SearchActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
