package com.github.melq.howmanydays.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.github.melq.howmanydays.data.DisplayMode
import java.time.LocalDateTime

class HowManyDaysViewModel : ViewModel() {
    private val _title = mutableStateOf("")
    private val _date = mutableStateOf(LocalDateTime.now())
    private val _displayMode = mutableStateOf(DisplayMode.DAYS)

    val title: State<String> = _title
    val date: State<LocalDateTime> = _date
    val displayMode: State<DisplayMode> = _displayMode

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setDate(date: LocalDateTime) {
        _date.value = date
    }

    fun setDisplayMode(displayMode: DisplayMode) {
        _displayMode.value = displayMode
    }
}