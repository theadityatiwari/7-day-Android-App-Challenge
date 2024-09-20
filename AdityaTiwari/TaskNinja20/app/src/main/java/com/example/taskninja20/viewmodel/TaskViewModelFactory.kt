package com.example.taskninja20.viewmodel

import androidx.lifecycle.ViewModelProvider
import com.example.taskninja20.data.repository.TaskRepository

class TaskViewModelFactory(val repository: TaskRepository): ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        return TaskViewModel(repository) as T
    }
}