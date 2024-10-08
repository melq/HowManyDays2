package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.data.entity.DayInfo
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme
import com.github.melq.howmanydays.viewmodel.HowManyDaysViewModel
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EditScreen(
    modifier: Modifier,
    viewModel: HowManyDaysViewModel,
    mode: EditMode,
    onNavigateToMain: () -> Unit
) {
    LaunchedEffect(mode) {
        if (mode == EditMode.Edit && viewModel.selectedDayInfo.value != null)
            viewModel.setParametersByDayInfo(viewModel.selectedDayInfo.value!!)
        else {
            viewModel.clearSelectedDayInfo()
            viewModel.setTitle("")
            viewModel.setDate(LocalDateTime.now())
            viewModel.setDisplayMode(DisplayMode.DAYS)
        }
    }
    HowManyDaysTheme {
        Surface {
            Column {
                val editedDayInfo = editForm(
                    modifier = modifier,
                    viewModel = viewModel,
                )
                Buttons(
                    modifier = modifier,
                    viewModel = viewModel,
                    mode = mode,
                    editedDayInfo = editedDayInfo,
                    onNavigateToMain = onNavigateToMain,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editForm(
    modifier: Modifier,
    viewModel: HowManyDaysViewModel,
): DayInfo {
    val title by viewModel.title
    val date by viewModel.date
    val displayMode by viewModel.displayMode
    TextField(
        value = title,
        onValueChange = { viewModel.setTitle(it) },
        label = { Text("Title") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(),
    )
    val state = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )
    var visible by remember { mutableStateOf(false) }
    if (visible) {
        DatePickerDialog(
            onDismissRequest = {
                visible = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        visible = false
                        viewModel.setDate(
                            LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(
                                    state.selectedDateMillis ?: Instant.now().toEpochMilli()
                                ),
                                java.util.TimeZone.getDefault().toZoneId()
                            )
                        )
                    },
                    modifier = modifier.padding(8.dp)
                ) {
                    Text(stringResource(android.R.string.ok))
                }
            },
        ) {
            DatePicker(state)
        }
    }
    TextField(
        value = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
        onValueChange = {},
        label = { Text("Date") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        readOnly = true,
        trailingIcon = {
            Box {
                TextButton(
                    onClick = { visible = true },
                ) {
                    Text("編集")
                }
            }
        },
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    )

    Text(
        text = "DisplayMode",
        fontSize = 12.sp,
        modifier = modifier.padding(start = 24.dp, top = 10.dp),
        color = TextFieldDefaults.colors().unfocusedLabelColor
    )
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (entry in DisplayMode.entries) {
            RadioButton(
                selected = displayMode == entry,
                onClick = {
                    viewModel.setDisplayMode(entry)
                },
            )
            Text(
                text = entry.toString(),
                modifier = modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }

    return DayInfo(viewModel.getCurrentDayInfoId(), title, date, displayMode)
}

@Composable
fun Buttons(
    modifier: Modifier,
    viewModel: HowManyDaysViewModel,
    editedDayInfo: DayInfo,
    mode: EditMode,
    onNavigateToMain: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    Row(horizontalArrangement = Arrangement.End, modifier = modifier.fillMaxWidth()) {
        TextButton(onClick = {
            onNavigateToMain()
        }) {
            Text(text = "キャンセル")
        }
        if (mode == EditMode.Add) {
            TextButton(onClick = {
                coroutineScope.launch {
                    viewModel.upsertDayInfo(editedDayInfo)
                    onNavigateToMain()
                }
            }) {
                Text(text = "登録")
            }
        } else {
            TextButton(onClick = {
                coroutineScope.launch {
                    viewModel.deleteDayInfo(viewModel.selectedDayInfo.value!!)
                    onNavigateToMain()
                }
            }) {
                Text(text = "削除")
            }
            TextButton(onClick = {
                coroutineScope.launch {
                    viewModel.upsertDayInfo(editedDayInfo)
                    onNavigateToMain()
                }
            }) {
                Text(text = "更新")
            }
        }
    }
}

@Preview
@Composable
fun EditScreenPreview() {
    EditScreen(
        modifier = Modifier,
        viewModel = viewModel(),
        mode = EditMode.Edit,
        onNavigateToMain = {})
}