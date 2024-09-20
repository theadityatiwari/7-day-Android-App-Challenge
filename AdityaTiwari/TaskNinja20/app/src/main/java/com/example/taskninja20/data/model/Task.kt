package com.example.taskninja20.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Automatically generated ID for each task
    val taskTitle: String = "",
    val taskDescription: String = "",
    val taskStatus: Int = 1, // 1: Pending, 2: In Progress , 3: Completed, 4: Cancelled etc.
    val priority: Int = 1, // 1: Low, 2: Medium, 3: high, etc.
    val taskCategory: Int = 1, // 1: Work, 2: Personal, 3: Urgent, etc.
)
