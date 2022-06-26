package com.mahmoudHesham.todoApp

import com.mahmoudHesham.todoApp.data.Todo

interface TodoInterface {
    fun onItemClicked(todo: Todo)
}