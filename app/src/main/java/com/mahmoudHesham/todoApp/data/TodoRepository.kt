package com.mahmoudHesham.todoApp.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) {

    val allTodos: Flow<List<Todo>> = todoDao.getTodos()

    fun getTodo(id: Int): Flow<Todo> = todoDao.getTodo(id)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(todo: Todo) {
        todoDao.insert(todo)
    }

    suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    suspend fun deleteByTitle(id: Int){
        todoDao.deleteByTitle(id)
    }

    suspend fun update(todo: Todo){
        todoDao.update(todo)
    }
}