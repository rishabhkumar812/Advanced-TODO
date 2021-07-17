package com.projects.advancedtodo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class TodoRecyclerViewAdapter(context: Context) : RecyclerView.Adapter<TodoRecyclerViewAdapter.TodoRecyclerViewHolder>() {

    private var todoItems:List<TodoEntity>? = null
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    class TodoRecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todoTitle:TextView = view.findViewById(R.id.todoTitle)
        val todoDesc:TextView = view.findViewById(R.id.description)
        val todoDeadLine:TextView = view.findViewById(R.id.deadline)
        val cardViewForTodo:CardView = view.findViewById(R.id.cardViewForTODO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoRecyclerViewHolder {
        val todoItemView:View =mInflater.inflate(R.layout.single_home_element, parent, false)
        return TodoRecyclerViewHolder(todoItemView)
    }

    override fun getItemCount(): Int {
        return if (todoItems!=null) todoItems!!.size
        else 0
    }

    override fun onBindViewHolder(holder: TodoRecyclerViewHolder, position: Int) {
        if(todoItems!=null){
            val currTodoItem = todoItems!![position]
            holder.todoTitle.text=currTodoItem.task
            holder.todoDesc.text=currTodoItem.descriptor
            holder.todoDeadLine.text=currTodoItem.deadline
            holder.cardViewForTodo.setOnClickListener {
            //TODO open details
            }
        }
    }

    fun setTODOItems(items:List<TodoEntity>) {
        todoItems=items
        notifyDataSetChanged()
    }
}
