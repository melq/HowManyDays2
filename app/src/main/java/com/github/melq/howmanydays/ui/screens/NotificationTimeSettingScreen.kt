package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.melq.howmanydays.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTimeSettingScreen(viewModel: SettingsViewModel, onNavigateBack: () -> Unit) {
        val settingsState by viewModel.notificationSettings.collectAsState()

        val currentSettings = settingsState ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                }
                return
        }

        val timePickerState =
                rememberTimePickerState(initialHour = currentSettings.hour, initialMinute = currentSettings.minute, is24Hour = true)

        var currentIsEnabled by remember(currentSettings.isEnabled) { mutableStateOf(currentSettings.isEnabled) }

        Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
                Row(
                        modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                ) {
                        Text("通知を有効にする", style = MaterialTheme.typography.bodyLarge)
                        Switch(
                                checked = currentIsEnabled,
                                onCheckedChange = { currentIsEnabled = it }
                        )
                }

                Text(
                        text = "通知時間の指定",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 32.dp)
                )

                TimePicker(state = timePickerState)

                Button(
                        onClick = {
                                viewModel.saveNotificationSettings(
                                        timePickerState.hour,
                                        timePickerState.minute,
                                        currentIsEnabled
                                )
                                onNavigateBack()
                        },
                        modifier = Modifier.padding(top = 32.dp)
                ) { Text("保存") }
        }
}
