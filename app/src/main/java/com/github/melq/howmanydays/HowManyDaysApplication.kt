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
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class HowManyDaysApplication : Application(), Configuration.Provider {
    lateinit var container: AppContainer
    private val scope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        observeNotificationSettings()
    }

    override val workManagerConfiguration: Configuration
        get() =
                Configuration.Builder()
                        .setWorkerFactory(MyWorkerFactory(container as AppDataContainer))
                        .build()

    private fun observeNotificationSettings() {
        scope.launch {
            val repository = container.notificationSettingsRepository
            repository.notificationSettings.collect { settings ->
                if (settings.isEnabled) {
                    scheduleDailyCheck(this@HowManyDaysApplication, settings.hour, settings.minute)
                } else {
                    WorkManager.getInstance(this@HowManyDaysApplication).cancelUniqueWork("DailyCheckWork")
                }
            }
        }
    }

    private fun scheduleDailyCheck(context: Context, hour: Int, minute: Int) {
        val now = Calendar.getInstance()
        val target =
                Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, hour)
                    set(Calendar.MINUTE, minute)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                    if (before(now)) {
                        add(Calendar.DAY_OF_YEAR, 1)
                    }
                }
        val initialDelay = target.timeInMillis - now.timeInMillis

        val workRequest =
                PeriodicWorkRequestBuilder<DailyCheckWorker>(24, TimeUnit.HOURS)
                        .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                        .build()

        WorkManager.getInstance(context)
                .enqueueUniquePeriodicWork(
                        "DailyCheckWork",
                        ExistingPeriodicWorkPolicy.UPDATE,
                        workRequest
                )
    }
}
