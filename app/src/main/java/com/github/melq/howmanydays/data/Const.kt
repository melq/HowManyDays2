package com.github.melq.howmanydays.data

class Const {
    companion object {
        const val NEW_DAY_INFO_ID = -1

        object TimeMilestones {
            val DaysMilestones: List<Long> = listOf(
                10,
                50,
                100,
                500,
                1000,
                2000,
                5000,
                10000,
                50000,
                100000,
                500000,
                1000000,
                5000000,
                10000000
            )
            val WeeksMilestones: List<Long> =
                listOf(10, 50, 100, 150, 300, 500, 1000, 2000, 5000, 10000, 50000, 100000, 1000000)
            val MonthsMilestones: List<Long> =
                listOf(1, 6, 12, 20, 50, 100, 200, 500, 1000, 5000, 10000, 50000, 100000)
            val YearsMilestones: List<Long> =
                listOf(1, 3, 5, 10, 50, 100, 200, 500, 1000, 5000, 10000, 100000, 1000000, 1000000)
        }
    }
}