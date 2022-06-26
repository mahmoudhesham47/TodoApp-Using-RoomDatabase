package com.mahmoudHesham.todoApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudHesham.todoApp.data.Todo
import com.mahmoudHesham.todoApp.databinding.TodoListItemBinding

class TodoListAdapter(private val todoInterface: TodoInterface) : ListAdapter<Todo, TodoListAdapter.TodoViewHolder>(
    DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.create(parent, todoInterface)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

    }

    class TodoViewHolder(binding: TodoListItemBinding, private val todoInterface: TodoInterface):RecyclerView.ViewHolder(binding.root) {
        private val todoTitle = binding.todoTitle
        private val deleteTodo = binding.deleteTodo

        var data: Todo? = null

        init {
            deleteTodo.setOnClickListener {
                data?.let {
                    todoInterface.onItemClicked(it)
                }
            }
        }

        fun bind(item: Todo?) {
            data = item
            todoTitle.text = item?.title
        }

        companion object {
            fun create(parent: ViewGroup,todoInterface: TodoInterface): TodoViewHolder {
                //return TodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item, parent, false))
                return TodoViewHolder(TodoListItemBinding.inflate(LayoutInflater.from(parent.context)), todoInterface)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}