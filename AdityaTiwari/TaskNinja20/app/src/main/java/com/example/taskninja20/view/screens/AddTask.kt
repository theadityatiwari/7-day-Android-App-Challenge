package com.example.taskninja20.view.screens

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.taskninja20.R
import com.example.taskninja20.data.database.TaskDatabase
import com.example.taskninja20.data.model.Task
import com.example.taskninja20.data.repository.TaskRepository
import com.example.taskninja20.reusable.CustomTextBold
import com.example.taskninja20.viewmodel.TaskViewModel
import com.example.taskninja20.viewmodel.TaskViewModelFactory
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavHostController) {
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf(TextFieldValue("")) }
    var selectedDate by remember { mutableStateOf("Select Deadline Date") }
    var taskStatus by remember { mutableStateOf("Pending") }
    var taskPriority by remember { mutableStateOf("Low") }
    var taskCategory by remember { mutableStateOf("Work") }
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()
    val formattedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: selectedDate

    val context = LocalContext.current
    val database = TaskDatabase.getInstance(context)
    val repository = TaskRepository(database.getTaskDao())
    val viewModel: TaskViewModel = viewModel(
        factory = TaskViewModelFactory(repository)
    )
    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row() {
            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .weight(.05f)
            )
            Box(modifier = Modifier.weight(.9f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    // Task Title
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextBold(
                        text = stringResource(id = R.string.add_task),
                        fontSize = 24.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = taskTitle,
                        onValueChange = { taskTitle = it },
                        label = { Text(stringResource(id = R.string.enter_title)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Select Deadline Date
                    OutlinedTextField(
                        value = formattedDate,
                        onValueChange = { },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = !showDatePicker }) {
                                Icon(
                                    Icons.Default.DateRange,
                                    contentDescription = stringResource(id = R.string.select_date)
                                )
                            }
                        },
                        readOnly = true
                    )

                    if (showDatePicker) {
                        Popup(
                            onDismissRequest = { showDatePicker = false },
                            alignment = Alignment.TopStart
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y = 64.dp)
                                    .shadow(elevation = 4.dp)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(16.dp)
                            ) {
                                DatePicker(
                                    state = datePickerState,
                                    showModeToggle = false,
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Task Status (Dropdown)
                    DropdownMenuField(
                        label = stringResource(id = R.string.task_status),
                        options = listOf(
                            stringResource(
                                id = R.string.pending
                            ), stringResource(
                                id = R.string.in_progress
                            ), stringResource(
                                id = R.string.completed
                            ), stringResource(
                                id = R.string.cancelled
                            )
                        ),
                        selectedOption = taskStatus,
                        onOptionSelected = { taskStatus = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Task Priority (Dropdown)
                    DropdownMenuField(
                        label = stringResource(id = R.string.task_priority),
                        options = listOf(
                            stringResource(
                                id = R.string.low
                            ),
                            stringResource(
                                id = R.string.medium
                            ),
                            stringResource(
                                id = R.string.high
                            )
                        ),
                        selectedOption = taskPriority,
                        onOptionSelected = { taskPriority = it }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Task Category (Dropdown)
                    DropdownMenuField(
                        label = stringResource(id = R.string.task_category),
                        options = listOf(
                            stringResource(
                                id = R.string.work
                            ),
                            stringResource(
                                id = R.string.personal
                            ),
                            stringResource(
                                id = R.string.urgent
                            )
                        ),
                        selectedOption = taskCategory,
                        onOptionSelected = { taskCategory = it }
                    )


                    Spacer(modifier = Modifier.height(16.dp))

                    // Task Description
                    OutlinedTextField(
                        value = taskDescription,
                        onValueChange = { taskDescription = it },
                        placeholder = { Text(stringResource(id = R.string.enter_description)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )

                    Spacer(modifier = Modifier.padding(bottom = 50.dp))

                    // Add Task Button
                    AddTaskButton(modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(), onClick = {
                        val task = Task(
                            taskTitle = taskTitle,
                            taskDescription = taskDescription.text,
                            taskStatus = when (taskStatus) {
                                "Pending" -> 1
                                "In-Progress" -> 2
                                "Completed" -> 3
                                "Cancelled" -> 4
                                else -> 1
                            },
                            priority = when (taskPriority) {
                                "Low" -> 1
                                "Medium" -> 2
                                "High" -> 3
                                else -> 1
                            },
                            taskCategory = when (taskCategory) {
                                "Work" -> 1
                                "Personal" -> 2
                                "Urgent" -> 3
                                else -> 1
                            }
                        )
                        viewModel.insertTask(task)
                        navController.popBackStack()
                    }
                    )

                }
            }

            Spacer(
                modifier = Modifier
                    .width(1.dp)
                    .weight(.05f)
            )
        }
    }

}

@Composable
fun AddTaskButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Text(stringResource(id = R.string.add_task), fontSize = 18.sp)
    }
}

@Composable
fun DropdownMenuField(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Select Option")
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(option)
                    expanded = false
                }, text = { Text(option) })
            }
        }
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
}
