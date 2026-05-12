package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(onNavigateToNotificationTime: () -> Unit) {
        Surface(modifier = Modifier.fillMaxSize()) {
                LazyColumn {
                        item {
                                Text(
                                        text = "設定",
                                        style = MaterialTheme.typography.headlineMedium,
                                        modifier = Modifier.padding(16.dp)
                                )
                        }
                        item {
                                SettingItem(
                                        title = "通知の設定",
                                        onClick = onNavigateToNotificationTime
                                )
                                HorizontalDivider()
                        }
                }
        }
}

@Composable
fun SettingItem(title: String, onClick: () -> Unit) {
        Row(
                modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
        ) { Text(text = title, style = MaterialTheme.typography.bodyLarge) }
}
