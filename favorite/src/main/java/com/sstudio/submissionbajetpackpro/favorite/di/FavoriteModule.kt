package com.sstudio.submissionbajetpackpro.favorite.di

import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.favorite.movie.FavoriteMovieViewModel
import com.sstudio.submissionbajetpackpro.favorite.tv.FavoriteTvShowViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MovieTvUseCase> { MovieTvInteractor(get()) }
}

val viewModelModule = module {
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvShowViewModel(get()) }
}