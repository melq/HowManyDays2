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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.melq.howmanydays.data.DayInfo
import com.github.melq.howmanydays.data.DisplayMode
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun EditScreen(
    modifier: Modifier,
    mode: EditMode,
    onNavigateToMain: () -> Unit
) {
    HowManyDaysTheme {
        Surface {
            Column {
                val dayInfo = editForm(modifier = modifier)
                Buttons(modifier = modifier, mode = mode, onNavigateToMain = onNavigateToMain)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun editForm(modifier: Modifier): DayInfo {
    val (title, setTitle) = remember { mutableStateOf("") }
    val (date, setDate) = remember { mutableStateOf(LocalDateTime.now()) }
    val (displayMode, setDisplayMode) = remember { mutableStateOf(DisplayMode.DAYS) }
    TextField(
        value = title,
        onValueChange = { setTitle(it) },
        label = { Text("Title") },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        ),
        modifier = modifier.padding(8.dp),
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
                        setDate(
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
        modifier = modifier.padding(8.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (mode in DisplayMode.entries) {
            RadioButton(
                selected = displayMode == mode,
                onClick = {
                    setDisplayMode(mode)
                },
            )
            Text(
                text = mode.toString(),
                modifier = modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }

    val dayInfo = DayInfo(title, date, displayMode)
    return dayInfo
}

@Composable
fun Buttons(modifier: Modifier, mode: EditMode, onNavigateToMain: () -> Unit) {
    Row(horizontalArrangement = Arrangement.End, modifier = modifier.fillMaxWidth()) {
        TextButton(onClick = { onNavigateToMain() }) {
            Text(text = "キャンセル")
        }
        if (mode == EditMode.Add) {
            TextButton(onClick = { onNavigateToMain() }) {
                Text(text = "登録")
            }
        } else {
            TextButton(onClick = { onNavigateToMain() }) {
                Text(text = "削除")
            }
            TextButton(onClick = { onNavigateToMain() }) {
                Text(text = "更新")
            }
        }
    }
}

@Preview
@Composable
fun EditScreenPreview() {
    EditScreen(modifier = Modifier, mode = EditMode.Edit, onNavigateToMain = {})
}