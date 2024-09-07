package com.github.melq.howmanydays

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.melq.howmanydays.ui.screens.EditMode
import com.github.melq.howmanydays.ui.screens.EditScreen
import com.github.melq.howmanydays.ui.screens.MainScreen
import com.github.melq.howmanydays.ui.screens.ScreenNames
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HowManyDaysApp(
) {
    HowManyDaysTheme {
        val navController = rememberNavController()
        Scaffold(
            topBar = {
                TopAppBar( // TODO: 別Composableに切り出す
                    colors = TopAppBarDefaults.largeTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            text = "TEST",
                        )
                    }
                )
            },
            content = { padding ->
                NavHost(
                    modifier = Modifier.padding(padding),
                    navController = navController,
                    startDestination = ScreenNames.MainScreen.name
                ) {
                    composable(route = ScreenNames.MainScreen.name) {
                        MainScreen(
                            modifier = Modifier,
                            onNavigateToEdit = { navController.navigate(ScreenNames.EditScreen.name) }
                        )
                    }
                    composable(route = ScreenNames.EditScreen.name) {
                        EditScreen(
                            modifier = Modifier,
                            mode = EditMode.Add,
                            onNavigateToMain = { navController.navigate(ScreenNames.MainScreen.name) }
                        )
                    }
                }
            }
        )
    }
}