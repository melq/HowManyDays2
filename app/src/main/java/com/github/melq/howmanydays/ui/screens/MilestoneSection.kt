package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.melq.howmanydays.viewmodel.HowManyDaysViewModel

@Composable
fun MilestoneSection(viewModel: HowManyDaysViewModel, modifier: Modifier = Modifier) {
    var newValue by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }
    val milestones = viewModel.milestones
    val displayMode by viewModel.displayMode

    Text(
            text = "通知タイミング",
            fontSize = 12.sp,
            modifier = modifier.padding(start = 24.dp, top = 20.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    TextField(
            value = newValue,
            onValueChange = {
                newValue = it
                isError = false
                errorMessage = ""
            },
            label = { Text("◯" + displayMode.label) },
            isError = isError,
            supportingText = { if (isError) Text(errorMessage) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors =
                    TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                    ),
            modifier = modifier.padding(8.dp).fillMaxWidth(),
            trailingIcon = {
                IconButton(
                        onClick = {
                            val value = newValue.toLongOrNull()
                            if (value != null) {
                                if (milestones.any { it.value == value }) {
                                    isError = true
                                    errorMessage = "すでに追加されています"
                                } else {
                                    viewModel.addMilestone(value)
                                    newValue = ""
                                    isError = false
                                    errorMessage = ""
                                }
                            } else {
                                isError = true
                                errorMessage = "数値を入力してください"
                            }
                        }
                ) { Icon(Icons.Default.Add, contentDescription = "Add Notification Timing") }
            }
    )

    LazyColumn(modifier = modifier.fillMaxWidth()) {
        items(milestones) { milestone ->
            Row(
                    modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                        text = "${milestone.value} ${displayMode.label}",
                        modifier = Modifier.weight(1f).padding(start = 16.dp)
                )
                IconButton(onClick = { viewModel.deleteMilestone(milestone) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete Notification Timing")
                }
            }
            HorizontalDivider(modifier = Modifier.padding(horizontal = 8.dp))
        }
    }
}
