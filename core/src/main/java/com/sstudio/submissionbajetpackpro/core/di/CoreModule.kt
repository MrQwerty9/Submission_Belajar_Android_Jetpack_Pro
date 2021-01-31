package com.sstudio.submissionbajetpackpro.core.di

import androidx.room.Room
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.data.source.remote.paging.movie.MovieDataSourceFactory
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
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
    single { RemoteDataSource(get(), get(), get(), get()) }
    single { MovieDataSourceFactory(get(), get(), get()) }
    factory { AppExecutors() }
    single { MovieTvRepository(get(), get(), get(), get()) }
}