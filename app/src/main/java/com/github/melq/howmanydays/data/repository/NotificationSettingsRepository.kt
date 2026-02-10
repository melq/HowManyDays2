package com.github.melq.howmanydays.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

interface INotificationSettingsRepository {
    val notificationHour: Flow<Int>
    val notificationMinute: Flow<Int>
    suspend fun saveNotificationTime(hour: Int, minute: Int)
}

class NotificationSettingsRepository(private val context: Context) :
        INotificationSettingsRepository {
    companion object {
        val HOUR = intPreferencesKey("notification_hour")
        val MINUTE = intPreferencesKey("notification_minute")
    }

    override val notificationHour: Flow<Int> =
            context.dataStore.data.map { preferences -> preferences[HOUR] ?: 9 }

    override val notificationMinute: Flow<Int> =
            context.dataStore.data.map { preferences -> preferences[MINUTE] ?: 0 }

    override suspend fun saveNotificationTime(hour: Int, minute: Int) {
        context.dataStore.edit { preferences ->
            preferences[HOUR] = hour
            preferences[MINUTE] = minute
        }
    }
}
