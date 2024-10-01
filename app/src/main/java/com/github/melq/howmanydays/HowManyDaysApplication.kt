package com.github.melq.howmanydays

import android.app.Application
import com.github.melq.howmanydays.data.AppContainer
import com.github.melq.howmanydays.data.AppDataContainer

class HowManyDaysApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}