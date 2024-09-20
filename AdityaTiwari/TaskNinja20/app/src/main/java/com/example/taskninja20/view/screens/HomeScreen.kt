package com.example.taskninja20.view.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.taskninja20.data.model.Screen
import com.example.taskninja20.data.model.bottomNavItems

@Composable
fun HomeScreen(navController: NavHostController) {
    var selected by rememberSaveable { mutableIntStateOf(0) }
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    LaunchedEffect(currentRoute ) {
        selected = when(currentRoute){
            Screen.Dashboard.route -> 0
            Screen.AllTasks.route -> 1
            else ->0
        }
    }

    Scaffold(
        floatingActionButton = {
            // Show FAB only if current route is Dashboard or AllTasks
            if (currentRoute != Screen.AddTask.route) {
                FloatingActionButton(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                    onClick = {
                        navController.navigate(Screen.AddTask.route)
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Task")
                }
            }
        },
        bottomBar = {
            // Show Bottom Navigation Bar only if current route is Dashboard or AllTasks
            if (currentRoute != Screen.AddTask.route) {
                NavigationBar() {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = index == selected,
                            onClick = {
                                selected = index
                                navController.navigate(item.route) {
                                    // Clear back stack and save state to prevent duplication
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selected) item.selectedIcon else item.unSelectedIcon,
                                    contentDescription = "title"
                                )
                            },
                            label = {
                                Text(text = item.title)
                            }
                        )
                    }
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(padding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(navController)
            }

            composable(Screen.AllTasks.route) {
                AllTasksScreen(navController)
            }

            composable(Screen.AddTask.route) {
                AddTaskScreen(navController)
            }
        }
    }
}



@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun previewTest() {

}



