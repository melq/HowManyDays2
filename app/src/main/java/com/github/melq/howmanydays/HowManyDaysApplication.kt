package com.github.melq.howmanydays

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.github.melq.howmanydays.data.AppContainer
import com.github.melq.howmanydays.data.AppDataContainer
import com.github.melq.howmanydays.workers.DailyCheckWorker
import com.github.melq.howmanydays.workers.MyWorkerFactory
import java.util.concurrent.TimeUnit

class HowManyDaysApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        scheduleDailyCheck(this)
    }

    private fun scheduleDailyCheck(context: Context) {
        val appDataContainer = AppDataContainer(context)
        val workerFactory = MyWorkerFactory(appDataContainer)

        val workRequest = PeriodicWorkRequestBuilder<DailyCheckWorker>(24, TimeUnit.HOURS)
            .build()

        WorkManager.initialize(
            context,
            Configuration.Builder().setWorkerFactory(workerFactory).build()
        )

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "DailyCheckWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}