package com.example.taskninja20.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.taskninja20.R

data class BottomNavItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

val bottomNavItems = listOf(
    BottomNavItem(
        title = "Dashboard",
        route = "dashboard",
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Filled.Home
    ),
    BottomNavItem(
        title = "All Task",
        route = "all_task",
        selectedIcon = Icons.Filled.List,
        unSelectedIcon = Icons.Filled.List
    ),
)