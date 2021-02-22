package com.sstudio.submissionbajetpackpro.ui.search.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sstudio.submissionbajetpackpro.R
import com.sstudio.submissionbajetpackpro.core.data.Resource
import com.sstudio.submissionbajetpackpro.ui.detail.DetailActivity
import com.sstudio.submissionbajetpackpro.ui.detail.DetailData
import kotlinx.android.synthetic.main.fragment_search_movie.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@FlowPreview
@ExperimentalCoroutinesApi
class SearchMovieFragment : Fragment() {

    private lateinit var movieAdapter: SearchMovieAdapter
    private val searchViewModel: SearchMovieViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = SearchMovieAdapter()
        movieAdapter.onItemClick = {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_DETAIL, DetailData(it.id, it.originalTitle, DetailData.Type.MOVIE))
            startActivity(intent)
        }

        lifecycleScope.launch {
            searchViewModel.listSearchMovie().observe(viewLifecycleOwner) { resource ->
                if (resource != null) {
                    when (resource) {
                        is Resource.Loading -> progress_bar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            progress_bar.visibility = View.GONE
                            resource.data?.let { movieAdapter.movies = it }
                            rv_search_movie.adapter = movieAdapter
                        }
                        is Resource.Error -> {
                            progress_bar.visibility = View.GONE
                            Toast.makeText(context, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        with(rv_search_movie){
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}