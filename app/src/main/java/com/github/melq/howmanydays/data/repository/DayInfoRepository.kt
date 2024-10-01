package com.github.melq.howmanydays.data.repository

import com.github.melq.howmanydays.data.dao.DayInfoDao
import com.github.melq.howmanydays.data.entity.DayInfo
import com.github.melq.howmanydays.data.repository.interfaces.IDayInfoRepository

class DayInfoRepository(private val dayInfoDao: DayInfoDao) : IDayInfoRepository {
    override suspend fun getAllDayInfos(): List<DayInfo> {
        return dayInfoDao.getAllDayInfos()
    }

    override suspend fun insertDayInfo(dayInfo: DayInfo) {
        dayInfoDao.insertDayInfo(dayInfo.title, dayInfo.date, dayInfo.displayMode.name)
    }

    override suspend fun updateDayInfo(dayInfo: DayInfo) {
        dayInfoDao.updateDayInfo(
            dayInfo.id,
            dayInfo.title,
            dayInfo.date,
            dayInfo.displayMode.name
        )
    }

    override suspend fun deleteDayInfo(dayInfo: DayInfo) {
        dayInfoDao.deleteDayInfo(dayInfo.id)
    }
}