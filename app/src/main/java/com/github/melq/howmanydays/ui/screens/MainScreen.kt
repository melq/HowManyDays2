package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.melq.howmanydays.data.DayInfo
import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(
    modifier: Modifier,
    onNavigateToEdit: () -> Unit
) {
    HowManyDaysTheme {
        Surface {
            DaysList(modifier)
            Box(modifier = modifier.fillMaxSize()) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    onClick = { onNavigateToEdit() }) {
                    Icon(Icons.Filled.Add, "Add")
                }
            }
        }
    }
}

@Composable
private fun DaysList(
    modifier: Modifier
) {
    val days = emptyList<DayInfo>().toMutableList()
    val displayModes = DisplayMode.entries.toTypedArray()
    for ((index, displayMode) in displayModes.withIndex()) {
        days += DayInfo(
            "TestTitle${index}",
            LocalDateTime.now().plusDays(index.toLong()),
            displayMode
        )
    }

    LazyColumn {
        items(days) { day ->
            DayItemRow(day, modifier)
            HorizontalDivider(
                modifier = Modifier
                    .height(1.dp)
                    .padding(8.dp, 0.dp),
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun DayItemRow(day: DayInfo, modifier: Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = { /*TODO*/ }) {
        Column(
            modifier = modifier
                .padding(8.dp)
        ) {
            Text(
                text = day.title,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = day.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
fun DaysListPreview() {
    HowManyDaysTheme {
        Surface {
            DaysList(modifier = Modifier)
        }
    }
}