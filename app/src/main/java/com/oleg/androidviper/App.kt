package com.oleg.androidviper

import android.app.Application
import com.oleg.androidviper.data.db.MovieDatabase
import com.squareup.picasso.BuildConfig
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router
import timber.log.Timber

lateinit var db: MovieDatabase

class App : Application() {
    lateinit var cicerone: Cicerone<Router>

    companion object {
        lateinit var INSTANCE: App
    }

    init {
        INSTANCE = this
    }

    override fun onCreate() {
        super.onCreate()
        db = MovieDatabase.getInstance(application = this)
        this.cicerone = Cicerone.create()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}