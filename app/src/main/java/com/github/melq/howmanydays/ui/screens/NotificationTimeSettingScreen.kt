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
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.melq.howmanydays.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTimeSettingScreen(viewModel: SettingsViewModel, onNavigateBack: () -> Unit) {
        val hour by viewModel.notificationHour.collectAsState()
        val minute by viewModel.notificationMinute.collectAsState()

        if (hour < 0 || minute < 0) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                }
                return
        }

        val timePickerState =
                rememberTimePickerState(initialHour = hour, initialMinute = minute, is24Hour = true)

        Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
        ) {
                Text(
                        text = "通知時間の指定",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 32.dp)
                )

                TimePicker(state = timePickerState)

                Button(
                        onClick = {
                                viewModel.saveNotificationTime(
                                        timePickerState.hour,
                                        timePickerState.minute
                                )
                                onNavigateBack()
                        },
                        modifier = Modifier.padding(top = 32.dp)
                ) { Text("保存") }
        }
}
