package com.github.melq.howmanydays.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class NotificationSettingsRepository(private val context: Context) {
    companion object {
        val HOUR = intPreferencesKey("notification_hour")
        val MINUTE = intPreferencesKey("notification_minute")
    }

    val notificationHour: Flow<Int> =
            context.dataStore.data.map { preferences -> preferences[HOUR] ?: 9 }

    val notificationMinute: Flow<Int> =
            context.dataStore.data.map { preferences -> preferences[MINUTE] ?: 0 }

    suspend fun saveNotificationTime(hour: Int, minute: Int) {
        context.dataStore.edit { preferences ->
            preferences[HOUR] = hour
            preferences[MINUTE] = minute
        }
    }
}
