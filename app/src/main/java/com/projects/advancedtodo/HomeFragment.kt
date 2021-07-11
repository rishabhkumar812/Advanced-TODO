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
        val myTodo = listOf<String>(
                "Study",
                "Football",
                "Driving",
                "Youtube",
                "Coursera")

        todoRecyclerViewAdapter.setTODOItems(myTodo)

        // TODO: Use the ViewModel
    }

}