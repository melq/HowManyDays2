package com.github.melq.howmanydays.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.melq.howmanydays.data.repository.INotificationSettingsRepository
import com.github.melq.howmanydays.data.repository.NotificationSettings
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
        private val notificationSettingsRepository: INotificationSettingsRepository
) : ViewModel() {

        val notificationSettings: StateFlow<NotificationSettings?> =
                notificationSettingsRepository.notificationSettings.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = null
                )

        fun saveNotificationSettings(hour: Int, minute: Int, isEnabled: Boolean) {
                viewModelScope.launch {
                        notificationSettingsRepository.saveNotificationSettings(hour, minute, isEnabled)
                }
        }
}
