package com.marcohc.tier.sample

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.marcohc.tier.sample.di.ApplicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

@Suppress("unused")
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        AndroidThreeTen.init(this)

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MainApplication)
            modules(ApplicationModule.modules)
        }
    }
}
