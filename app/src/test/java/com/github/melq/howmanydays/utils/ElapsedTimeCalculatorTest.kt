package com.github.melq.howmanydays.utils

import com.github.melq.howmanydays.data.DisplayMode
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

class ElapsedTimeCalculatorTest {

    /**
     * 日数単位での経過時間計算が正しく行われるかテストします。
     * （例：9.5日経過している場合は、切り捨てられて9日となること）
     */
    @Test
    fun calculateElapsedTime_days() {
        val date = LocalDateTime.of(2023, 1, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 10, 12, 0)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.DAYS, now)
        assertEquals(9L, result)
    }

    /**
     * 同日同時刻の場合、経過日数が0日となるかテストします。
     */
    @Test
    fun calculateElapsedTime_days_zero() {
        val date = LocalDateTime.of(2023, 1, 1, 12, 0)
        val now = LocalDateTime.of(2023, 1, 1, 12, 0)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.DAYS, now)
        assertEquals(0L, result)
    }

    /**
     * 週単位での経過時間計算が正しく行われるかテストします。
     * （例：21.5日経過している場合は、3週となること）
     */
    @Test
    fun calculateElapsedTime_weeks() {
        val date = LocalDateTime.of(2023, 1, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 22, 12, 0)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.WEEKS, now)
        assertEquals(3L, result)
    }

    /**
     * 7日未満（1週間未満）の経過時間の場合、0週となるかテストします。
     */
    @Test
    fun calculateElapsedTime_weeks_lessThanOneWeek() {
        val date = LocalDateTime.of(2023, 1, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 7, 23, 59)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.WEEKS, now)
        assertEquals(0L, result)
    }

    /**
     * 月単位での経過時間計算が正しく行われるかテストします。
     * （例：2ヶ月と数日経過している場合、2ヶ月となること）
     */
    @Test
    fun calculateElapsedTime_months() {
        val date = LocalDateTime.of(2023, 1, 15, 0, 0)
        val now = LocalDateTime.of(2023, 3, 20, 12, 0)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.MONTHS, now)
        assertEquals(2L, result)
    }

    /**
     * ちょうど1ヶ月未満の場合に0ヶ月として計算される境界値テストです。
     * （例：1月1日から1月31日の場合はまだ1ヶ月経過していないため0ヶ月となること）
     */
    @Test
    fun calculateElapsedTime_months_boundary() {
        val date = LocalDateTime.of(2023, 1, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 31, 23, 59)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.MONTHS, now)
        assertEquals(0L, result)
    }

    /**
     * 年単位での経過時間計算が正しく行われるかテストします。
     * （例：3年と1ヶ月経過している場合、3年となること）
     */
    @Test
    fun calculateElapsedTime_years() {
        val date = LocalDateTime.of(2020, 5, 1, 0, 0)
        val now = LocalDateTime.of(2023, 6, 1, 12, 0)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.YEARS, now)
        assertEquals(3L, result)
    }

    /**
     * ちょうど1年未満の場合に0年として計算される境界値テストです。
     * （例：閏年の2月29日開始で、翌年の2月28日の場合は0年となること）
     */
    @Test
    fun calculateElapsedTime_years_boundary() {
        val date = LocalDateTime.of(2020, 2, 29, 0, 0)
        val now = LocalDateTime.of(2021, 2, 28, 23, 59)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.YEARS, now)
        assertEquals(0L, result)
    }

    /**
     * 未来の日付が指定された場合（マイナスの経過時間）の計算が正しく行われるかテストします。
     * （例：基準日から見て現在が30日前の場合、-30日となること）
     */
    @Test
    fun calculateElapsedTime_futureDate() {
        val date = LocalDateTime.of(2023, 2, 1, 0, 0)
        val now = LocalDateTime.of(2023, 1, 1, 12, 0)
        val result = ElapsedTimeCalculator.calculateElapsedTime(date, DisplayMode.DAYS, now)
        assertEquals(-30L, result)
    }
}
