package com.github.melq.howmanydays.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
        tableName = "milestone",
        foreignKeys =
                [
                        ForeignKey(
                                entity = DayInfo::class,
                                parentColumns = ["id"],
                                childColumns = ["dayInfoId"],
                                onDelete = ForeignKey.CASCADE
                        )],
        indices = [Index("dayInfoId")]
)
data class Milestone(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val dayInfoId: Int,
        val value: Long,
        val isNotified: Boolean = false
)
