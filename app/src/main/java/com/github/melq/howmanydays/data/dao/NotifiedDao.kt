package com.github.melq.howmanydays.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.github.melq.howmanydays.data.entity.Notified

@Dao
interface NotifiedDao {
    @Query("SELECT * FROM notified WHERE dayInfoId = :dayInfoId AND deleted = 0 ORDER BY id ASC")
    suspend fun getNotifiedByDayInfoId(dayInfoId: Int): List<Notified>

    @Query("INSERT INTO notified (dayInfoId, displayMode, milestone, deleted) VALUES (:dayInfoId, :displayMode, :milestone, '0')")
    suspend fun insertNotified(dayInfoId: Int, displayMode: String, milestone: Long)
}