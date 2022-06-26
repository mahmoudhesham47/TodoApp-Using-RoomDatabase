package com.mahmoudHesham.todoApp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahmoudHesham.todoApp.data.Todo
import com.mahmoudHesham.todoApp.databinding.ActivityMainBinding
import com.mahmoudHesham.todoApp.viewModel.TodoViewModel
import com.mahmoudHesham.todoApp.viewModel.TodoViewModelFactory

class MainActivity : AppCompatActivity(), TodoInterface {

    private lateinit var binding: ActivityMainBinding
    private val todoViewModel: TodoViewModel by viewModels {
        TodoViewModelFactory((application as TodoApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.cardView.visibility = View.GONE

        val recyclerView = binding.recyclerView
        val adapter = TodoListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        todoViewModel.allTodos.observe(this) { todos ->
            todos.let { adapter.submitList(it) }
        }

        binding.imageView.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("delete All")
            builder.setMessage("are you sure you want to delete all to-dos?")
            builder.setPositiveButton("Yes"){_, _ ->
                todoViewModel.deleteTodo()
                Toast.makeText(applicationContext,"All todos are deleted",Toast.LENGTH_LONG).show()
            }
            builder.setNegativeButton("No"){_, _ ->
                Toast.makeText(applicationContext,"OPERATION CANCELLED",Toast.LENGTH_LONG).show()
            }

            val alertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }

        binding.floatingActionButton.setOnClickListener {
            binding.cardView.visibility = View.VISIBLE
            showSoftKeyboard(binding.todoEntered)
            val addTodo = binding.todoEntered
            binding.saveTodo.setOnClickListener {
                if (binding.todoEntered.text.isEmpty()){
                    binding.cardView.visibility = View.GONE
                    binding.todoEntered.text.clear()
                    hideKeyboard(binding.todoEntered)
                    Toast.makeText(applicationContext, "Nothing was entered", Toast.LENGTH_LONG).show()
                } else {
                    todoViewModel.addNewTodo(addTodo.text.toString(), true)
                    binding.cardView.visibility = View.GONE
                    binding.todoEntered.text.clear()
                    hideKeyboard(binding.todoEntered)
                    Toast.makeText(applicationContext, "Todo added successfully", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showSoftKeyboard(view: View) {
        if (view.requestFocus()) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onItemClicked(todo: Todo) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("DELETE")
        builder.setMessage("ARE YOU SURE YOU WANT TO DELETE \"${todo.title.trim()}\"")
        builder.setPositiveButton("YES"){ _, _ ->
            todoViewModel.deleteByTitle(todo.id)
            Toast.makeText(applicationContext, "${todo.title.trim()} successfully deleted", Toast.LENGTH_LONG).show()
        }
        builder.setNegativeButton("NO"){ _, _ ->
            Toast.makeText(applicationContext, "Nothing was deleted", Toast.LENGTH_SHORT).show()
        }
        val alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
   }
}