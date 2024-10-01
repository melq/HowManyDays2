package com.github.melq.howmanydays.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.data.entity.DayInfo
import com.github.melq.howmanydays.data.repository.interfaces.IDayInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class HowManyDaysViewModel(private val dayInfoRepository: IDayInfoRepository) : ViewModel() {
    private val _title = mutableStateOf("")
    private val _date = mutableStateOf(LocalDateTime.now())
    private val _displayMode = mutableStateOf(DisplayMode.DAYS)
    private val _selectedDayInfo = mutableStateOf(null as DayInfo?)
    private val _dayInfos = mutableStateOf(emptyList<DayInfo>())

    val title: State<String> = _title
    val date: State<LocalDateTime> = _date
    val displayMode: State<DisplayMode> = _displayMode
    val selectedDayInfo: State<DayInfo?> = _selectedDayInfo
    val dayInfos: State<List<DayInfo>> = _dayInfos

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

    fun getCurrentDayInfoId(): Int {
        return _selectedDayInfo.value?.id ?: -1
    }

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

    fun fetchDayInfos() {
        viewModelScope.launch(Dispatchers.IO) {
            _dayInfos.value = dayInfoRepository.getAllDayInfos()
        }
    }

    suspend fun upsertDayInfo(dayInfo: DayInfo) {
        if (dayInfo.id == -1)
            dayInfoRepository.insertDayInfo(dayInfo)
        else
            dayInfoRepository.updateDayInfo(dayInfo)
    }

    suspend fun deleteDayInfo(dayInfo: DayInfo) {
        dayInfoRepository.deleteDayInfo(dayInfo)
    }
}