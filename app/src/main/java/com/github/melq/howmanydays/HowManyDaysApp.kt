package com.github.melq.howmanydays

import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.melq.howmanydays.ui.screens.EditMode
import com.github.melq.howmanydays.ui.screens.EditScreen
import com.github.melq.howmanydays.ui.screens.MainScreen
import com.github.melq.howmanydays.ui.screens.ScreenNames
import com.github.melq.howmanydays.ui.theme.HowManyDaysTheme
import com.github.melq.howmanydays.viewmodel.HowManyDaysViewModel

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
                    composable(
                        route = ScreenNames.MainScreen.name
                    ) {
                        val viewModel: HowManyDaysViewModel =
                            viewModel(LocalContext.current as ComponentActivity)
                        MainScreen(
                            modifier = Modifier,
                            viewModel = viewModel,
                            onNavigateToEdit = { mode: EditMode ->
                                navController.navigate("${ScreenNames.EditScreen.name}/${mode.ordinal}")
                            }
                        )
                    }
                    composable(
                        route = "${ScreenNames.EditScreen.name}/{mode}",
                        arguments = listOf(navArgument("mode") { NavType.StringType })
                    ) { backStackEntry ->
                        val viewModel: HowManyDaysViewModel =
                            viewModel(LocalContext.current as ComponentActivity)
                        EditScreen(
                            modifier = Modifier,
                            viewModel = viewModel,
                            mode = EditMode.fromOrdinal(
                                (backStackEntry.arguments?.getString("mode") ?: "0").toInt()
                            ),
                            onNavigateToMain = {
                                navController.popBackStack(
                                    ScreenNames.MainScreen.name,
                                    false
                                )
                            }
                        )
                    }
                }
            }
        )
    }
}