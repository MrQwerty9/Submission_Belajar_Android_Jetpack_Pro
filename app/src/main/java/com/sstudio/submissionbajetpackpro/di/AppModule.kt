package com.sstudio.submissionbajetpackpro.di

import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.detail.DetailViewModel
import com.sstudio.submissionbajetpackpro.ui.movie.MovieViewModel
import com.sstudio.submissionbajetpackpro.ui.tv.TvViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieTvUseCase> {MovieTvInteractor(get())}
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}