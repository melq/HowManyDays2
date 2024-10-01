package com.github.melq.howmanydays.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.github.melq.howmanydays.HowManyDaysApplication

object HowManyDaysViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HowManyDaysViewModel(dayInfoRepository = howManyDaysApplication().container.dayInfoRepository)
        }
    }
}

fun CreationExtras.howManyDaysApplication(): HowManyDaysApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as HowManyDaysApplication)