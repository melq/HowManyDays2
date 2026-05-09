package com.github.melq.howmanydays.utils

import com.github.melq.howmanydays.data.DisplayMode
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ElapsedTimeCalculatorTest {

    @Test
    fun calculateElapsedTime_days() {
        val date = LocalDateTime.of(2023, 1, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 10, 12, 0) // 9.5 days later
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.DAYS, now)
        assertEquals(9L, result)
    }

    @Test
    fun calculateElapsedTime_weeks() {
        val date = LocalDateTime.of(2023, 1, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 22, 12, 0) // 21.5 days later -> 3 weeks
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.WEEKS, now)
        assertEquals(3L, result)
    }

    @Test
    fun calculateElapsedTime_months() {
        val date = LocalDateTime.of(2023, 1, 15, 0, 0)
        val now = LocalDateTime.of(2023, 3, 20, 12, 0) // 2 months and a few days
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.MONTHS, now)
        assertEquals(2L, result)
    }

    @Test
    fun calculateElapsedTime_years() {
        val date = LocalDateTime.of(2020, 5, 1, 0, 0)
        val now = LocalDateTime.of(2023, 6, 1, 12, 0) // 3 years and 1 month
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.YEARS, now)
        assertEquals(3L, result)
    }

    @Test
    fun calculateElapsedTime_negative() {
        val date = LocalDateTime.of(2023, 2, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 1, 12, 0) // Before the date
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.DAYS, now)
        assertEquals(-30L, result) // -30 days
    }
}
