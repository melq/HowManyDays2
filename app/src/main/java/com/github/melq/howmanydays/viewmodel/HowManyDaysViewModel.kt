package com.github.melq.howmanydays.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.melq.howmanydays.data.DayInfo
import com.github.melq.howmanydays.data.DisplayMode
import java.time.LocalDateTime

class HowManyDaysViewModel : ViewModel() {
    private val _title = mutableStateOf("")
    private val _date = mutableStateOf(LocalDateTime.now())
    private val _displayMode = mutableStateOf(DisplayMode.DAYS)
    private val _selectedDayInfo = mutableStateOf(null as DayInfo?)

    private var dayInfos = initDayInfos()

    val title: State<String> = _title
    val date: State<LocalDateTime> = _date
    val displayMode: State<DisplayMode> = _displayMode
    val selectedDayInfo: State<DayInfo?> = _selectedDayInfo

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDate(date: LocalDateTime) {
        _date.value = date
    }

    fun setDisplayMode(displayMode: DisplayMode) {
        _displayMode.value = displayMode
    }

    fun setSelectedDayInfo(dayInfo: DayInfo) {
        _selectedDayInfo.value = dayInfo
    }

    fun clearSelectedDayInfo() {
        _selectedDayInfo.value = null
    }

    fun setParametersByDayInfo(dayInfo: DayInfo) {
        setTitle(dayInfo.title)
        setDate(dayInfo.date)
        setDisplayMode(dayInfo.displayMode)
    }

    fun upsertDayInfo(dayInfo: DayInfo) {
        setTitle("aaa")
    }

    fun getDayInfos(): List<DayInfo> {
        return dayInfos
    }

    private fun initDayInfos(): List<DayInfo> {
        val days = emptyList<DayInfo>().toMutableList()
        val displayModes = DisplayMode.entries.toTypedArray()
        for ((index, displayMode) in displayModes.withIndex()) {
            days += DayInfo(
                "TestTitle: $index",
                LocalDateTime.now().plusDays(index.toLong()),
                displayMode
            )
        }
        return days
    }
}