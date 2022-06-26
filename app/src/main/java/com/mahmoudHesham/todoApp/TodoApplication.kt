package com.mahmoudHesham.todoApp

import android.app.Application
import com.mahmoudHesham.todoApp.data.TodoRepository
import com.mahmoudHesham.todoApp.data.TodoRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class TodoApplication: Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database: TodoRoomDatabase by lazy {
        TodoRoomDatabase.getDatabase(this, applicationScope)
    }

    val repository: TodoRepository by lazy {
        TodoRepository(database.todoDao())
    }
}