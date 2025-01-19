package com.github.melq.howmanydays.utils

import com.github.melq.howmanydays.data.DisplayMode
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class ElapsedTimeCalculator {
    companion object {
        fun calculateElapsedTime(date: LocalDateTime, displayMode: DisplayMode): Long {
            val now = LocalDateTime.now()
            val duration = Duration.between(date, now)

            return when (displayMode) {
                DisplayMode.DAYS -> duration.toDays()
                DisplayMode.WEEKS -> duration.toDays() / 7
                DisplayMode.MONTHS -> ChronoUnit.MONTHS.between(date, now)
                DisplayMode.YEARS -> ChronoUnit.YEARS.between(date, now)
            }
        }
    }
}