package com.oleg.androidviper

import android.app.Application
import com.oleg.androidviper.data.db.MovieDatabase
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber

class App : Application() {
    lateinit var cicerone: Cicerone<Router>
    lateinit var db: MovieDatabase

    companion object {
        lateinit var INSTANCE: App
    }

    override fun onCreate() {
        super.onCreate()
        db = MovieDatabase.getInstance(application = this)
        this.cicerone = Cicerone.create()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        INSTANCE = this
    }
}