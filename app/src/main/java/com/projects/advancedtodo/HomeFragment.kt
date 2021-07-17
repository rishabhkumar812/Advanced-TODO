package com.projects.advancedtodo

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDateTime

class HomeFragment : Fragment() {

    lateinit var viewModel: HomeViewModel
    lateinit var todoRecyclerView: RecyclerView
    lateinit var todoRecyclerViewAdapter: TodoRecyclerViewAdapter
    lateinit var deleteAllFAB:FloatingActionButton
    lateinit var addNewFAB:FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        todoRecyclerView=view.findViewById(R.id.todoRecyclerView)
        deleteAllFAB=view.findViewById(R.id.deleteAllFAB)
        addNewFAB=view.findViewById(R.id.addNewFAB)

        if(activity!=null) {
            todoRecyclerViewAdapter = TodoRecyclerViewAdapter(activity as Context)
            todoRecyclerView.layoutManager=GridLayoutManager(activity,2)
            todoRecyclerView.adapter=todoRecyclerViewAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        //fake data
        val myTodo = listOf<TodoEntity>(
                TodoEntity("Study","Complete my next novel.","12:00 PM - 10 Jan 2021"),
                TodoEntity("Football","Practice for tournament.","12:00 AM - 21 Nov 2021"),
                TodoEntity("Driving","Practice driving before my DL test.","12:00 PM - 14 Jun 2021"),
                TodoEntity("Youtube","Create my new youtube channel for vlogs","09:00 AM - 31 Dec 2021"),
                TodoEntity("Coursera","Complete my Moocs course for 4 credits","12:00 AM - 16 Aug 2021"))

        todoRecyclerViewAdapter.setTODOItems(myTodo)

        // TODO: Use the ViewModel
    }

}