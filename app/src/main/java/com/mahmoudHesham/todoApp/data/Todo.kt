package com.mahmoudHesham.todoApp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String,

    val isDone: Boolean = false

) {
    constructor(title: String) : this(0,title, true)
}
