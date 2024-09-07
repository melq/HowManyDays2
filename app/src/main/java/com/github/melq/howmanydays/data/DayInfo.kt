package com.github.melq.howmanydays.data

import java.time.LocalDateTime

data class DayInfo(
    val title: String,
    val date: LocalDateTime/* Todo: LocalDate */,
    val displayMode: DisplayMode
)
