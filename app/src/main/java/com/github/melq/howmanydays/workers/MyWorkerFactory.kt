package com.github.melq.howmanydays.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.github.melq.howmanydays.data.AppDataContainer

class MyWorkerFactory(private val appDataContainer: AppDataContainer) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return when (workerClassName) {
            DailyCheckWorker::class.java.name -> DailyCheckWorker(
                appContext,
                workerParameters,
                appDataContainer
            )

            else -> throw IllegalArgumentException("Unknown worker class: $workerClassName")
        }
    }
}