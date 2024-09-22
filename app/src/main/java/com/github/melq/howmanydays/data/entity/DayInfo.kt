package com.github.melq.howmanydays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.melq.howmanydays.data.Const
import com.github.melq.howmanydays.data.DisplayMode
import java.time.LocalDateTime

@Entity(tableName = "day_info")
data class DayInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = Const.NEW_DAY_INFO_ID,
    val title: String,
    val date: LocalDateTime,
    val displayMode: DisplayMode,
    val deleted: Boolean = false
)
