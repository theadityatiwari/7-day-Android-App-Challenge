package com.example.taskninja20.data.repository

import androidx.lifecycle.LiveData
import com.example.taskninja20.data.database.TaskDao
import com.example.taskninja20.data.model.Task

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task:Task){
        return taskDao.insertTask(task)
    }
    suspend fun updateTask(task:Task){
        return taskDao.updateTask(task)
    }

    suspend fun deleteTask(taskId:Int){
        return taskDao.deleteTask(taskId)
    }

    fun getAllTasks(): LiveData<List<Task>> {
        return taskDao.getAllTasks()
    }

}