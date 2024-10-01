package com.github.melq.howmanydays.data

import android.content.Context
import com.github.melq.howmanydays.data.database.DayInfoDatabase
import com.github.melq.howmanydays.data.repository.DayInfoRepository
import com.github.melq.howmanydays.data.repository.interfaces.IDayInfoRepository

interface AppContainer {
    val dayInfoRepository: IDayInfoRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val dayInfoRepository: IDayInfoRepository by lazy {
        DayInfoRepository(DayInfoDatabase.getDatabase(context).dayInfoDao())
    }

}