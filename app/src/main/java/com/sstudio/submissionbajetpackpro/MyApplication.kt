package com.sstudio.submissionbajetpackpro

import android.app.Application
import com.sstudio.submissionbajetpackpro.core.di.databaseModule
import com.sstudio.submissionbajetpackpro.core.di.networkModule
import com.sstudio.submissionbajetpackpro.core.di.repositoryModule
import com.sstudio.submissionbajetpackpro.di.useCaseModule
import com.sstudio.submissionbajetpackpro.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}