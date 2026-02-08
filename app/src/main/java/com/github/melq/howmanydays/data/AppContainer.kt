package com.github.melq.howmanydays.data

import android.content.Context
import com.github.melq.howmanydays.data.database.DayInfoDatabase
import com.github.melq.howmanydays.data.repository.DayInfoRepository
import com.github.melq.howmanydays.data.repository.NotifiedRepository
import com.github.melq.howmanydays.data.repository.interfaces.IDayInfoRepository
import com.github.melq.howmanydays.data.repository.interfaces.INotifiedRepository

interface AppContainer {
    val dayInfoRepository: IDayInfoRepository
    val notifiedRepository: INotifiedRepository
    val notificationSettingsRepository: NotificationSettingsRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val dayInfoRepository: IDayInfoRepository by lazy {
        DayInfoRepository(DayInfoDatabase.getDatabase(context).dayInfoDao())
    }
    override val notifiedRepository: INotifiedRepository by lazy {
        NotifiedRepository(DayInfoDatabase.getDatabase(context).notifiedDao())
    }
    override val notificationSettingsRepository: NotificationSettingsRepository by lazy {
        NotificationSettingsRepository(context)
    }
}
