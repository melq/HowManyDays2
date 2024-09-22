package com.github.melq.howmanydays.data.repository

import com.github.melq.howmanydays.data.dao.DayInfoDao
import com.github.melq.howmanydays.data.entity.DayInfo

class DayInfoRepository(private val dayInfoDao: DayInfoDao) {
    suspend fun getAllDayInfos(): List<DayInfo> {
        return dayInfoDao.getAllDayInfos()
    }
}