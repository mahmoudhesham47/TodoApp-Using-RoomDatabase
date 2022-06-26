package com.mahmoudHesham.todoApp.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo ORDER BY title ASC")
    fun getTodos(): Flow<List<Todo>>

    @Query("SELECT * FROM todo WHERE id= :id")
    fun getTodo(id: Int): Flow<Todo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(todo: Todo)

    @Query("DELETE FROM todo ")
    suspend fun deleteAll()

    @Query("DELETE FROM todo WHERE id= :id")
    suspend fun deleteByTitle(id: Int)

    @Update
    suspend fun update(todo: Todo)
}