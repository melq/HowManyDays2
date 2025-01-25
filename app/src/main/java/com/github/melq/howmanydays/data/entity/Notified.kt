package com.github.melq.howmanydays.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.melq.howmanydays.data.DisplayMode

@Entity(tableName = "notified")
data class Notified(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val dayInfoId: Int,
    val displayMode: DisplayMode,
    val milestone: Long,
    val deleted: Boolean = false
)
