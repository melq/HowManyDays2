package com.github.melq.howmanydays.data

import java.time.LocalDateTime

data class DayInfo(
    val id: Int = Const.NEW_DAY_INFO_ID,
    val title: String,
    val date: LocalDateTime,
    val displayMode: DisplayMode
)
