package com.github.melq.howmanydays.data

import android.content.Context
import com.github.melq.howmanydays.data.database.DayInfoDatabase
import com.github.melq.howmanydays.data.repository.DayInfoRepository
import com.github.melq.howmanydays.data.repository.IDayInfoRepository
import com.github.melq.howmanydays.data.repository.IMilestoneRepository
import com.github.melq.howmanydays.data.repository.INotificationSettingsRepository
import com.github.melq.howmanydays.data.repository.MilestoneRepository
import com.github.melq.howmanydays.data.repository.NotificationSettingsRepository

interface AppContainer {
    val dayInfoRepository: IDayInfoRepository
    val notificationSettingsRepository: INotificationSettingsRepository
    val milestoneRepository: IMilestoneRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val dayInfoRepository: IDayInfoRepository by lazy {
        DayInfoRepository(DayInfoDatabase.getDatabase(context).dayInfoDao())
    }
    override val milestoneRepository: IMilestoneRepository by lazy {
        MilestoneRepository(DayInfoDatabase.getDatabase(context).milestoneDao())
    }
    override val notificationSettingsRepository: INotificationSettingsRepository by lazy {
        NotificationSettingsRepository(context)
    }
}
