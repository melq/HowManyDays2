package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    mode: EditMode
) {
    HowManyDaysTheme {
        Surface {
            Column {
                val dayInfo = EditForm(modifier = modifier)
                Buttons(modifier = modifier, mode = mode)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditForm(modifier: Modifier): DayInfo {
    var title = ""
    var date = LocalDateTime.now()
    var displayMode = DisplayMode.DAYS
    TextField(
        value = "",
        onValueChange = { title = it },
        modifier = modifier.padding(8.dp),
        label = { Text("Title") }
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
                        date = LocalDateTime.ofInstant(
                            Instant.ofEpochMilli(state.selectedDateMillis!!),
                            java.util.TimeZone.getDefault().toZoneId()
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
        modifier = modifier.padding(8.dp),
        label = { Text("Date") },
        readOnly = true,
        trailingIcon = {
            Box {
                TextButton(
                    onClick = { visible = true },
                ) {
                    Text("編集")
                }
            }
        }
    )
    // TODO: DisplayModeForm
    return DayInfo(title, date, displayMode)
}

@Composable
fun Buttons(modifier: Modifier, mode: EditMode) {
    // TODO: CancelButton
    if (mode == EditMode.Add) {
        // TODO: AddButton
    } else {
        // TODO: DeleteButton
        // TODO: EditButton
    }
}

@Preview
@Composable
fun EditScreenPreview() {
    EditScreen(modifier = Modifier, mode = EditMode.Add)
}