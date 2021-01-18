package com.sstudio.submissionbajetpackpro.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sstudio.submissionbajetpackpro.core.di.Injection
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.detail.DetailViewModel
import com.sstudio.submissionbajetpackpro.ui.favorite.movie.FavoriteMovieViewModel
import com.sstudio.submissionbajetpackpro.ui.favorite.tv.FavoriteTvShowViewModel
import com.sstudio.submissionbajetpackpro.ui.movie.MovieViewModel
import com.sstudio.submissionbajetpackpro.ui.tv.TvViewModel

class ViewModelFactory private constructor(private val movieTvUseCase: MovieTvUseCase) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideMovieTvUseCase(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                MovieViewModel(movieTvUseCase) as T
            }
            modelClass.isAssignableFrom(TvViewModel::class.java) -> {
                TvViewModel(movieTvUseCase) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(movieTvUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                FavoriteMovieViewModel(movieTvUseCase) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                FavoriteTvShowViewModel(movieTvUseCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
