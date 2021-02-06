package com.sstudio.submissionbajetpackpro.core.di

import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sstudio.submissionbajetpackpro.core.BuildConfig
import com.sstudio.submissionbajetpackpro.core.data.MovieTvRepository
import com.sstudio.submissionbajetpackpro.core.data.source.local.LocalDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.local.room.MovieTvDatabase
import com.sstudio.submissionbajetpackpro.core.data.source.remote.RemoteDataSource
import com.sstudio.submissionbajetpackpro.core.data.source.remote.api.ApiService
import com.sstudio.submissionbajetpackpro.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val databaseModule = module {
    val MIGRATION_5_6 = object : Migration(5, 6) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `ListTypeEntity_New` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `idMovieTv` INTEGER NOT NULL, `listType` TEXT NOT NULL)"
            )
            database.execSQL(
                "INSERT INTO ListTypeEntity_New (id, idMovieTv, listType) SELECT id, idMovieTv, listType FROM ListTypeEntity"
            )
            database.execSQL("DROP TABLE ListTypeEntity")
            database.execSQL("ALTER TABLE ListTypeEntity_New RENAME TO ListTypeEntity")
        }
    }
    val MIGRATION_6_7 = object : Migration(6, 7) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "CREATE TABLE IF NOT EXISTS `TvEntity_new` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `backdropPath` TEXT NOT NULL, `firstAirDate` TEXT NOT NULL, `genreIds` TEXT NOT NULL, `idTv` INTEGER NOT NULL, `originalName` TEXT NOT NULL, `overview` TEXT NOT NULL, `posterPath` TEXT NOT NULL, `voteAverage` REAL NOT NULL)"
            )
            database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_TvEntity_idTv` ON `TvEntity_new` (`idTv`)")
            database.execSQL(
                "INSERT OR REPLACE INTO TvEntity_new (id, backdropPath, firstAirDate, genreIds, idTv, originalName, overview, posterPath, voteAverage) SELECT id, backdropPath, firstAirDate, genreIds, idTv, originalName, overview, posterPath, voteAverage FROM TvEntity"
            )
            database.execSQL("DROP TABLE TvEntity")
            database.execSQL("ALTER TABLE TvEntity_new RENAME TO TvEntity")
        }
    }
    factory { get<MovieTvDatabase>().movieTvDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieTvDatabase::class.java, "MovieTvDb.db"
        )
//            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_5_6)
            .addMigrations(MIGRATION_6_7)
            .build()
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
    factory { AppExecutors() }
    single { MovieTvRepository(get(), get(), get()) }
}