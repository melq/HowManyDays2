package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.github.melq.howmanydays.viewmodel.HowManyDaysViewModel

@Composable
fun MilestoneSection(viewModel: HowManyDaysViewModel, modifier: Modifier = Modifier) {
    var newValue by remember { mutableStateOf("") }
    val milestones = viewModel.milestones

    Text(
            text = "マイルストーン設定",
            style = MaterialTheme.typography.titleMedium,
            modifier = modifier.padding(vertical = 8.dp)
    )

    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        TextField(
                value = newValue,
                onValueChange = { newValue = it },
                label = { Text("経過時間") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f)
        )
        IconButton(
                onClick = {
                    val value = newValue.toLongOrNull()
                    if (value != null) {
                        viewModel.addMilestone(value)
                        newValue = ""
                    }
                }
        ) { Icon(Icons.Default.Add, contentDescription = "Add Milestone") }
    }

    milestones.forEach { milestone ->
        Row(
                modifier = modifier.fillMaxWidth().padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                    text = "${milestone.value} ${viewModel.displayMode.value.label}",
                    modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { viewModel.deleteMilestone(milestone) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete Milestone")
            }
        }
        HorizontalDivider()
    }
}
