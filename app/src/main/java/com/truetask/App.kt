package com.truetask

import android.app.Application
import com.facebook.stetho.Stetho
import com.truetask.common.CommonModule
import com.truetask.games.GamesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
        setupStetho()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                CommonModule.create(),
                GamesModule.create()
            )
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    private fun setupStetho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }
}