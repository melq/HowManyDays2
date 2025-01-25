package com.github.melq.howmanydays.data.repository

import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.data.dao.NotifiedDao
import com.github.melq.howmanydays.data.entity.Notified
import com.github.melq.howmanydays.data.repository.interfaces.INotifiedRepository

class NotifiedRepository(private val notifiedDao: NotifiedDao) : INotifiedRepository {
    override suspend fun getNotifiedListByDayInfoId(dayInfoId: Int): List<Notified> {
        return notifiedDao.getNotifiedByDayInfoId(dayInfoId)
    }

    override suspend fun insertNotified(dayInfoId: Int, displayMode: DisplayMode, milestone: Long) {
        notifiedDao.insertNotified(dayInfoId, displayMode.name, milestone)
    }
}