package com.sstudio.submissionbajetpackpro.di

import androidx.room.Room
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvInteractor
import com.sstudio.submissionbajetpackpro.core.domain.usecase.MovieTvUseCase
import com.sstudio.submissionbajetpackpro.ui.detail.DetailViewModel
import com.sstudio.submissionbajetpackpro.ui.favorite.movie.FavoriteMovieViewModel
import com.sstudio.submissionbajetpackpro.ui.favorite.tv.FavoriteTvShowViewModel
import com.sstudio.submissionbajetpackpro.ui.movie.MovieViewModel
import com.sstudio.submissionbajetpackpro.ui.tv.TvViewModel
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val useCaseModule = module {
    factory<MovieTvUseCase> {MovieTvInteractor(get())}
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}