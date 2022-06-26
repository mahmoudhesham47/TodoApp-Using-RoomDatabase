package com.mahmoudHesham.todoApp.viewModel

import androidx.lifecycle.*
import com.mahmoudHesham.todoApp.data.Todo
import com.mahmoudHesham.todoApp.data.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val allTodos : LiveData<List<Todo>> = repository.allTodos.asLiveData()

    private fun getNewTodo(todoTitle:String, todoIsDone:Boolean) : Todo{
        return Todo(
            title = todoTitle,
            isDone = todoIsDone
        )
    }
    private fun insertTodo(todo: Todo){
        viewModelScope.launch {
            repository.insert(todo)
        }
    }
    fun addNewTodo(todoTitle:String,  todoIsDone:Boolean){
        insertTodo(getNewTodo(todoTitle,todoIsDone))
    }

    fun deleteByTitle(id: Int){
        viewModelScope.launch {
            repository.deleteByTitle(id)
        }
    }

    fun deleteTodo(){
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}

class TodoViewModelFactory(private val repository: TodoRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TodoViewModel::class.java)){
            @Suppress("UNCHECKED CAST")
            return TodoViewModel(repository) as T
        } else {
            throw IllegalArgumentException("UNKNOWN VIEW MODEL CLASS")
        }
    }
}