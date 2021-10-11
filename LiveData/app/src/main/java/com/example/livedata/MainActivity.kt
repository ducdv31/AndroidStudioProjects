package com.example.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName
    private lateinit var btn: Button
    private val liveData: MutableLiveData<ArrayList<User>> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = getListUser()
        liveData.value = list

        btn = findViewById(R.id.btn)

        liveData.observe(this) {
            Log.e(TAG, "onCreate: $it")
        }

        btn.setOnClickListener {
//            list[0].id.a = Random.nextInt()
            liveData.value = list
        }
    }

    private fun getListUser(): ArrayList<User> {
        val list: ArrayList<User> = arrayListOf()

        list.add(
            User(
                "Duc ${Random.nextInt(0, 5)}", ID(
                    Random.nextInt(
                        6,
                        10
                    ), Random.nextInt(11, 15)
                )
            )
        )
        list.add(
            User(
                "Duc ${Random.nextInt(0, 5)}", ID(
                    Random.nextInt(
                        6,
                        10
                    ), Random.nextInt(11, 15)
                )
            )
        )

        return list
    }
}