package com.example.taskninja20.view.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.taskninja20.data.database.TaskDatabase
import com.example.taskninja20.data.model.Task
import com.example.taskninja20.data.repository.TaskRepository
import com.example.taskninja20.reusable.CustomTextBold
import com.example.taskninja20.reusable.CustomTextMedium
import com.example.taskninja20.utils.Constants
import com.example.taskninja20.viewmodel.TaskViewModel
import com.example.taskninja20.viewmodel.TaskViewModelFactory

@Composable
fun AllTasksScreen(navController: NavHostController) {

    val context = LocalContext.current
    val database = TaskDatabase.getInstance(context)
    val repository = TaskRepository(database.getTaskDao())
    val viewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(repository)
    )

    val tasks by viewModel.tasks.observeAsState(emptyList())

    Scaffold(content = { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column() {
                Spacer(
                    modifier = Modifier
                        .height(16.dp)
                )
                CustomTextBold(
                    text = "All Tasks",
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(
                    modifier = Modifier
                        .height(25.dp)
                )

                // Implement the LazyColumn with fade and scale effect
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(tasks.size) { index ->
                        val animationProgress = remember {
                            Animatable(0f)
                        }

                        LaunchedEffect(Unit) {
                            animationProgress.animateTo(1f)
                        }

                        val scale = animateFloatAsState(
                            targetValue = if (animationProgress.value > 0.3f) 1.05f else 0.9f, // Increased scale
                            animationSpec = tween(durationMillis = 700)
                        )
                        val alpha = animateFloatAsState(
                            targetValue = animationProgress.value,
                            animationSpec = tween(durationMillis = 700)
                        )

                        TaskCard(
                            task = tasks[index],
                            modifier = Modifier
                                .graphicsLayer(
                                    scaleX = scale.value,
                                    scaleY = scale.value,
                                    alpha = alpha.value
                                )
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    })

}

@Composable
fun TaskCard(task: Task, modifier: Modifier = Modifier) {

    val isDarkTheme = isSystemInDarkTheme()

    val cardColor = if (isDarkTheme) {
        Color(0xFF2E2E2E) // Dark Gray
    } else {
        Color(0xFFFFD08A) // Light Yellow
    }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(160.dp)
            .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardColor // Set the background color here
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Task Status Icon
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .fillMaxSize()
                    .weight(.8f),
            ) {
                // Task Title
                Text(
                    text = task.taskTitle,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(top = 8.dp)
                )
                // Task Description
                Text(
                    text = task.taskDescription,
                    fontSize = 14.sp,
                    maxLines = 2,
                    minLines = 2,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.weight(1f))
                // Task Category
                CustomTextMedium(
                    text = when (task.taskCategory) {
                        1 -> "Work"
                        2 -> "Personal"
                        3 -> "Urgent"
                        else -> "Other"
                    },
                    fontSize = 14.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(.15f)
                    .fillMaxHeight()
                    .padding(bottom = 10.dp, end = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Icon(
                    imageVector = when (task.taskStatus) {
                        1 -> Icons.Filled.Settings
                        2 -> Icons.Filled.PlayArrow
                        3 -> Icons.Filled.CheckCircle
                        4 -> Icons.Filled.Close
                        else -> Icons.Filled.Info
                    },
                    contentDescription = "Check",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(top = 9.dp)
                        .size(27.dp)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .width(50.dp)
                        .height(26.dp)
                        .background(
                            color = when (task.priority) {
                                1 -> Color(0xFF4CAF50) // Low - Green
                                2 -> Color(0xFFFFC107) // Medium - Yellow
                                3 -> Color(0xFFF44336) // High - Red
                                else -> Color.Gray
                            },
                            shape = RoundedCornerShape(16.dp)
                        )
                ) {
                    Text(
                        text = when (task.priority) {
                            Constants.LOW -> "Low"
                            Constants.MID -> "Mid"
                            Constants.HIGH -> "High"
                            else -> {
                                "info"
                            }
                        },
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold, modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

            }

        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun previewTask() {
    TaskCard(
        Task(
            taskTitle = "Write Documentation",
            taskDescription = "Update the project documentation.This is a sample task description and i want task design to be minimal and that's why spending more time in this ",
            taskStatus = 4,
            priority = 3,
            taskCategory = 1
        )
    )
}