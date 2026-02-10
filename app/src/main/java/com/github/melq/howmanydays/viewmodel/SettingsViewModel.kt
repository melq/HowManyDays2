package com.github.melq.howmanydays.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.melq.howmanydays.data.repository.INotificationSettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
        private val notificationSettingsRepository: INotificationSettingsRepository
) : ViewModel() {

        val notificationHour: StateFlow<Int> =
                notificationSettingsRepository.notificationHour.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = -1
                )

        val notificationMinute: StateFlow<Int> =
                notificationSettingsRepository.notificationMinute.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = -1
                )

        fun saveNotificationTime(hour: Int, minute: Int) {
                viewModelScope.launch {
                        notificationSettingsRepository.saveNotificationTime(hour, minute)
                }
        }
}
