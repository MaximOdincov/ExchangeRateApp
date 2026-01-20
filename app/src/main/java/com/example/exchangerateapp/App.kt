package com.example.exchangerateapp

import android.app.Application
import com.example.exchangerateapp.di.dataModule
import com.example.exchangerateapp.di.domainModule
import com.example.exchangerateapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            androidLogger(Level.ERROR)
            modules(
                dataModule,
                domainModule,
                presentationModule
            )
        }
    }
}