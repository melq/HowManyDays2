package com.github.melq.howmanydays.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.melq.howmanydays.R
import com.github.melq.howmanydays.data.AppDataContainer
import com.github.melq.howmanydays.data.Const
import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.utils.ElapsedTimeCalculator.Companion.calculateElapsedTime

class DailyCheckWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val appDataContainer: AppDataContainer
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        val dayInfoRepository = appDataContainer.dayInfoRepository
        val dayInfos = dayInfoRepository.getAllDayInfos()

        for (dayInfo in dayInfos) {
            val elapsedTime = calculateElapsedTime(dayInfo.date, dayInfo.displayMode)
            if (checkElapsedTime(elapsedTime, dayInfo.displayMode))
                sendNotification(
                    "HowManyDays",
                    "${dayInfo.title}から${elapsedTime}" +
                            "${
                                when (dayInfo.displayMode) {
                                    DisplayMode.DAYS -> "日"
                                    DisplayMode.WEEKS -> "週"
                                    DisplayMode.MONTHS -> "ヶ月"
                                    DisplayMode.YEARS -> "年"
                                }
                            }が経過しました！"
                )
        }
        return Result.success()
    }

    private fun checkElapsedTime(elapsedTime: Long, displayMode: DisplayMode): Boolean {
        return when (displayMode) {
            DisplayMode.DAYS -> Const.Companion.TimeMilestones.DaysMilestones.contains(elapsedTime)
            DisplayMode.WEEKS -> Const.Companion.TimeMilestones.WeeksMilestones.contains(elapsedTime)
            DisplayMode.MONTHS -> Const.Companion.TimeMilestones.MonthsMilestones.contains(
                elapsedTime
            )

            DisplayMode.YEARS -> Const.Companion.TimeMilestones.YearsMilestones.contains(elapsedTime)
        }
    }

    private fun sendNotification(title: String, message: String) {
        val channelId = "daily_check_channel"
        val notificationId = title.hashCode()

        val channel =
            NotificationChannel(channelId, "Reminder", NotificationManager.IMPORTANCE_DEFAULT)
        val manager = applicationContext.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.howmanydays_round_icon)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            with(NotificationManagerCompat.from(applicationContext)) {
                notify(notificationId, builder.build())
            }
        }
    }
}