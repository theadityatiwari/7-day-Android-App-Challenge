package com.example.taskninja20.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskninja20.data.model.Task
import com.example.taskninja20.data.repository.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    val tasks : LiveData<List<Task>>
        get() = repository.getAllTasks()


    fun insertTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertTask(task)
        }
    }


    fun updateTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(task)
        }
    }

    fun deleteTask(taskId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTask(taskId)
        }
    }


}