package com.github.melq.howmanydays.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.melq.howmanydays.HowManyDaysApplication

object HowManyDaysViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val container = howManyDaysApplication().container
            HowManyDaysViewModel(
                    dayInfoRepository = container.dayInfoRepository,
                    milestoneRepository = container.milestoneRepository
            )
        }
        initializer {
            SettingsViewModel(
                    notificationSettingsRepository =
                            howManyDaysApplication().container.notificationSettingsRepository
            )
        }
    }
}

fun CreationExtras.howManyDaysApplication(): HowManyDaysApplication =
        (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HowManyDaysApplication)
