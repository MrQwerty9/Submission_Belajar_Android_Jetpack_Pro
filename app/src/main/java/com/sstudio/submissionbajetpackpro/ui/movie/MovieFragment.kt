package com.sstudio.submissionbajetpackpro.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.source.remote.ApiResponse
import com.sstudio.submissionbajetpackpro.core.ui.movie.MovieAdapter
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private val viewModel: MovieViewModel by viewModel()

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
            with(rv_list_movie) {
                layoutManager = LinearLayoutManager(context)
//                setHasFixedSize(true)
                adapter = movieAdapter
            }
//            viewModel.state.observe(viewLifecycleOwner, {
//                Log.d("mytag", "repo status")
//                Toast.makeText(context, "STtus", Toast.LENGTH_SHORT).show()
//                when (it.status) {
//                    NetworkState.Status.LOADING -> {
//                        Log.d("mytag", "observe loading")
//                        progress_bar.visibility = View.VISIBLE
//                    }
//                    NetworkState.Status.SUCCESS -> {
//                        Log.d("mytag", "observe success")
//                        progress_bar.visibility = View.GONE
//                    }
//                    NetworkState.Status.EMPTY -> {
//                        Log.d("mytag", "observe empty")
//                        progress_bar.visibility = View.GONE
//                    }
//                    else -> {
//                        Log.d("mytag", "observe ${it.msg}")
//                        progress_bar.visibility = View.GONE
//                        Toast.makeText(context, "Terjadi Kesalahan ${it.msg}", Toast.LENGTH_SHORT).show()
//                    }
//                }
//            })

            viewModel.listMovie?.observe(viewLifecycleOwner) { resource ->
                if (resource != null) {
                    when (resource) {
                        is ApiResponse.Success -> {
                        Log.d("mytag", "observe success")
                            movieAdapter.submitList(resource.data)
                        }
                        is ApiResponse.Empty -> {
                            ApiResponse.Empty
                        Log.d("mytag", "observe empty")
                        }
                        is ApiResponse.Failed -> {
                            ApiResponse.Failed(resource.errorMessage)
                        Log.d("mytag", "observe fail ${resource.errorMessage}")
                        }
                    }
                }
            }
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
