package com.example.catslistcoroutines

import android.app.Application
import com.example.catslistcoroutines.dependencyinjection.appModules
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}