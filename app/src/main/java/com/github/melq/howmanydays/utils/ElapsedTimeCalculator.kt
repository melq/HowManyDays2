package com.github.melq.howmanydays.utils

import com.github.melq.howmanydays.data.DisplayMode
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

/**
 * 基準日時から現在日時までの経過時間を計算するユーティリティクラスです。
 */
class ElapsedTimeCalculator {
    companion object {
        /**
         * 基準日時と現在日時の差分から、指定された単位（日、週、月、年）での経過時間を計算します。
         * 端数は切り捨てられます。（例：1.5ヶ月の差分がある場合、月単位の計算結果は1となります）
         *
         * @param date 経過時間の計算の基準となる日時
         * @param displayMode 取得したい経過時間の単位（DAYS, WEEKS, MONTHS, YEARS）
         * @param now 現在の日時（単体テスト時に時間を固定するために使用します）
         * @return 指定された単位での経過時間。dateがnowより未来の場合はマイナスの値が返ります。
         */
        fun calculateElapsedTime(date: LocalDateTime, displayMode: DisplayMode, now: LocalDateTime = LocalDateTime.now()): Long {
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