package de.abauer.giphy_clean_architecture

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import de.abauer.giphy_androidcleanarchitecture.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class GiphyArchApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}