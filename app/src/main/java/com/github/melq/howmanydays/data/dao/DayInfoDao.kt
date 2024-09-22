package com.github.melq.howmanydays.data.dao

import androidx.room.Dao
import androidx.room.Query
import com.github.melq.howmanydays.data.entity.DayInfo

@Dao
interface DayInfoDao {
    @Query("SELECT * FROM day_info WHERE deleted = 0 ORDER BY id ASC")
    fun getAllDayInfos(): List<DayInfo>
}