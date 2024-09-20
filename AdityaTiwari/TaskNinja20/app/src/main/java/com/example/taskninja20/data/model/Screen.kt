package com.example.taskninja20.data.model

sealed class Screen(val route: String) {
    data object Splash : Screen("splash_screen")
    data object Home : Screen("home_screen")
    data object Dashboard : Screen("dashboard")
    data object AllTasks : Screen("all_task")
    data object AddTask : Screen("add_task_screen")
    data object UpdateTask : Screen("update_task_screen/{taskId}") {
        fun createRoute(taskId: Int) = "update_task_screen/$taskId"
    }
}
