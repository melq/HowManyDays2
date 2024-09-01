package com.github.melq.howmanydays.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme

@Composable
fun EditScreen(
    modifier: Modifier,
    mode: EditMode
) {
    HowManyDaysTheme {
        Surface {
            Column {
                EditForm(modifier = modifier)
                Buttons(modifier = modifier, mode = mode)
                Text(text = "This is EditScreen!")
            }
        }
    }
}

@Composable
fun EditForm(modifier: Modifier) {
    // TODO: TitleForm
    // TODO: DateForm
    // TODO: DisplayModeForm
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