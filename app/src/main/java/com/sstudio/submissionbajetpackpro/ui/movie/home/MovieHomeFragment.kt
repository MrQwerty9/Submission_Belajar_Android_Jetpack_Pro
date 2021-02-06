package com.sstudio.submissionbajetpackpro.ui.movie.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.core.utils.Params
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.movie.list.MovieListActivity
import com.sstudio.submissionbajetpackpro.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_movie_home.*
import org.koin.android.viewmodel.ext.android.viewModel

class MovieHomeFragment : Fragment() {

    private lateinit var homeParentAdapter: MovieHomeParentAdapter
    private val viewModel: MovieHomeViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {

            setHasOptionsMenu(true)
            observeData()
            homeParentAdapter = MovieHomeParentAdapter()
            swipe_layout.setOnRefreshListener {
                viewModel.refresh()
                observeData()
                swipe_layout.isRefreshing = false
            }

            homeParentAdapter.onItemClick = {
                val intent = Intent(context, MovieListActivity::class.java)
                intent.putExtra(MovieListActivity.PARAMS_EXTRA, Params.MovieParams(listType = it))
                startActivity(intent)
            }
            homeParentAdapter.onItemMovieClick = {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_MOVIE_TV, DetailActivity.IS_MOVIE)
                intent.putExtra(DetailActivity.EXTRA_DETAIL, it.id)
                startActivity(intent)
            }
            with(rv_list_movie) {
                layoutManager = LinearLayoutManager(context)
//                setHasFixedSize(true)
                adapter = homeParentAdapter
            }
        }
    }

    private fun observeData(){
        viewModel.listMovie?.observe(viewLifecycleOwner) {
            val listMovieHome = it.map { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        Log.d("mytag", "loading")
                        progress_bar.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        Log.d("mytag", "loading")
                        progress_bar.visibility = View.GONE
                    }
                    is Resource.Error -> {
                        Log.d("mytag", "loading")
                        progress_bar.visibility = View.GONE
                        Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
                resource.data
            }
            homeParentAdapter.updateList(listMovieHome)
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
