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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.melq.howmanydays.data.DayInfo
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme
import com.github.melq.howmanydays.viewmodel.HowManyDaysViewModel
import java.time.format.DateTimeFormatter

@Composable
fun MainScreen(
    modifier: Modifier,
    viewModel: HowManyDaysViewModel = viewModel(),
    onNavigateToEdit: (EditMode) -> Unit
) {
    HowManyDaysTheme {
        Surface {
            DayInfosList(
                modifier = modifier,
                viewModel = viewModel,
                onNavigateToEdit = onNavigateToEdit
            )
            Box(modifier = modifier.fillMaxSize()) {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    onClick = { onNavigateToEdit(EditMode.Add) }) {
                    Icon(Icons.Filled.Add, "Add")
                }
            }
        }
    }
}

@Composable
private fun DayInfosList(
    modifier: Modifier = Modifier,
    viewModel: HowManyDaysViewModel,
    onNavigateToEdit: (EditMode) -> Unit
) {
    val dayInfos = viewModel.getDayInfos()
    LazyColumn {
        items(dayInfos) { dayInfo ->
            DayItemRow(modifier = modifier, dayInfo = dayInfo) {
                onNavigateToEdit(EditMode.Edit)
            }
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
private fun DayItemRow(
    modifier: Modifier = Modifier,
    dayInfo: DayInfo,
    onNavigateToEdit: (EditMode) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = { onNavigateToEdit(EditMode.Edit) }) {
        Column(
            modifier = modifier
                .padding(8.dp)
        ) {
            Text(
                text = dayInfo.title,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = dayInfo.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
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
            MainScreen(modifier = Modifier, onNavigateToEdit = {})
        }
    }
}