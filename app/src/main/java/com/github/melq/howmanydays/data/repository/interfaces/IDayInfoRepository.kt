package com.github.melq.howmanydays.data.repository.interfaces

import com.github.melq.howmanydays.data.entity.DayInfo

interface IDayInfoRepository {
    suspend fun getAllDayInfos(): List<DayInfo>

    suspend fun insertDayInfo(dayInfo: DayInfo)

    suspend fun updateDayInfo(dayInfo: DayInfo)

    suspend fun deleteDayInfo(dayInfo: DayInfo)
}