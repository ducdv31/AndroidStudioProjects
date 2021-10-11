package com.example.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.livedata.adapter.UserAdapter
import com.example.livedata.model.ID
import com.example.livedata.model.User
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var btn: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn = findViewById(R.id.btn)
        recyclerView = findViewById(R.id.recyclerView)
        userAdapter = UserAdapter(this)
        val lllm = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView.apply {
            layoutManager = lllm
            adapter = userAdapter
        }

        val list = getListUser()
        userAdapter.setData(list)

        btn.setOnClickListener {

        }
    }

    private fun getListUser(): ArrayList<User> {
        val list: ArrayList<User> = arrayListOf()

        list.add(User("Hi", arrayListOf(ID(4, 5)), true))
        list.add(User("Ok", arrayListOf(ID(7, 8)), true))
        list.add(User("Hello", arrayListOf(ID(6, 8)), true))
        list.add(User("Duc", arrayListOf(ID(9, 7)), true))

        return list
    }
}