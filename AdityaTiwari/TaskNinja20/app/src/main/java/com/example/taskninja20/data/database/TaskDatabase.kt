package com.example.taskninja20.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskninja20.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun getTaskDao(): TaskDao

    companion object{
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        fun getInstance(context: Context):TaskDatabase{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context,
                    TaskDatabase::class.java,
                    "task_database")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}