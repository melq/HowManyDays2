package com.github.melq.howmanydays.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

data class NotificationSettings(
    val hour: Int,
    val minute: Int,
    val isEnabled: Boolean
)

interface INotificationSettingsRepository {
    val notificationSettings: Flow<NotificationSettings>
    suspend fun saveNotificationSettings(hour: Int, minute: Int, isEnabled: Boolean)
}

class NotificationSettingsRepository(private val context: Context) :
        INotificationSettingsRepository {
    companion object {
        val HOUR = intPreferencesKey("notification_hour")
        val MINUTE = intPreferencesKey("notification_minute")
        val IS_ENABLED = booleanPreferencesKey("notification_enabled")
    }

    override val notificationSettings: Flow<NotificationSettings> =
            context.dataStore.data.map { preferences ->
                NotificationSettings(
                    hour = preferences[HOUR] ?: 9,
                    minute = preferences[MINUTE] ?: 0,
                    isEnabled = preferences[IS_ENABLED] ?: true
                )
            }

    override suspend fun saveNotificationSettings(hour: Int, minute: Int, isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[HOUR] = hour
            preferences[MINUTE] = minute
            preferences[IS_ENABLED] = isEnabled
        }
    }
}
