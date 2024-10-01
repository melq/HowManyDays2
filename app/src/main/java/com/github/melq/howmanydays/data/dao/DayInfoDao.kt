package com.github.melq.howmanydays.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.github.melq.howmanydays.data.entity.DayInfo
import java.time.LocalDateTime

@Dao
interface DayInfoDao {
    @Query("SELECT * FROM day_info WHERE deleted = 0 ORDER BY id ASC")
    fun getAllDayInfos(): List<DayInfo>

    @Query("INSERT INTO day_info (title, date, displayMode, deleted) VALUES (:title, :date, :displayMode, '0')")
    suspend fun insertDayInfo(title: String, date: LocalDateTime, displayMode: String)

    @Query("UPDATE day_info SET title = :title, date = :date, displayMode = :displayMode WHERE id = :id")
    suspend fun updateDayInfo(id: Int, title: String, date: LocalDateTime, displayMode: String)

    @Query("UPDATE day_info SET deleted = 1 WHERE id = :id")
    suspend fun deleteDayInfo(id: Int)
}