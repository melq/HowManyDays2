package com.github.melq.howmanydays.data.repository.interfaces

import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.data.entity.Notified

interface INotifiedRepository {
    suspend fun getNotifiedListByDayInfoId(dayInfoId: Int): List<Notified>
    suspend fun insertNotified(dayInfoId: Int, displayMode: DisplayMode, milestone: Long)
}