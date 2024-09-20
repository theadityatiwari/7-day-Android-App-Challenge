package com.example.taskninja20.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.compose.runtime.Composable

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskninja20.data.model.Screen
import com.example.taskninja20.ui.theme.TaskNinja20Theme
import com.example.taskninja20.view.screens.AddTaskScreen
import com.example.taskninja20.view.screens.AllTasksScreen
import com.example.taskninja20.view.screens.DashboardScreen
import com.example.taskninja20.view.screens.HomeScreen
import com.example.taskninja20.view.screens.SplashScreen


class EntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskNinja20Theme {
                EntryActivityScreen()
            }
        }
    }
}

@Composable
fun EntryActivityScreen() {
    val navController = rememberNavController()
    val navControllerBottomBar = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {

        composable(Screen.Splash.route) {
            SplashScreen(navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(navControllerBottomBar)
        }


        composable(Screen.UpdateTask.route) { backStackEntry ->
//            val taskId = backStackEntry.arguments?.getString("taskId")?.toInt()
//            UpdateTaskScreen(navController, taskId)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    TaskNinja20Theme {
        EntryActivityScreen()
    }
}