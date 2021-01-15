package com.sstudio.submissionbajetpackpro.di

import androidx.room.Room
import com.sstudio.submissionbajetpackpro.BuildConfig
import com.sstudio.submissionbajetpackpro.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.data.source.local.room.MovieTvDatabase
import com.sstudio.submissionbajetpackpro.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.ui.detail.DetailViewModel
import com.sstudio.submissionbajetpackpro.ui.favorite.movie.FavoriteMovieViewModel
import com.sstudio.submissionbajetpackpro.ui.favorite.tv.FavoriteTvShowViewModel
import com.sstudio.submissionbajetpackpro.ui.movie.MovieViewModel
import com.sstudio.submissionbajetpackpro.ui.tv.TvViewModel
import com.sstudio.submissionbajetpackpro.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    factory { get<MovieTvDatabase>().movieTvDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieTvDatabase::class.java, "MovieTvDb.db"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get(), get()) }
    factory { AppExecutors() }
    single { MovieTvRepository(get(), get(), get()) }
}

val viewModelModule = module {
    viewModel { MovieViewModel(get()) }
    viewModel { TvViewModel(get()) }
    viewModel { FavoriteMovieViewModel(get()) }
    viewModel { FavoriteTvShowViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}