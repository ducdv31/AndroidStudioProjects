package com.example.roomdatabase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.UserManager
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.database.UserDatabase
import com.example.roomdatabase.user2.MasterUser

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var rcvUserAdapter: RcvUserAdapter
    lateinit var name: EditText
    lateinit var age: EditText
    lateinit var set: Button
    lateinit var deleteAll: Button
    /* update */

    lateinit var search: EditText

    private lateinit var listUser: MutableList<User>

    private val startIntent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                loadData()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* ******************************************************** */
        recyclerView = findViewById(R.id.rcv_user_database)
        name = findViewById(R.id.et_name)
        age = findViewById(R.id.et_age)
        set = findViewById(R.id.set_data)
        deleteAll = findViewById(R.id.delete_all_user)
        search = findViewById(R.id.et_search)
        rcvUserAdapter = RcvUserAdapter(object : RcvUserAdapter.IClickUser {
            override fun onClickUpdate(user: User) {
                val intent = Intent(this@MainActivity, UpdateActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable("user", user)
                intent.putExtras(bundle)
                startIntent.launch(intent)
            }

            override fun onClickDeleteUser(user: User) {
                UserDatabase.getInstance(this@MainActivity).userDAO().deleteUser(user)
                loadData()
            }

        })

        listUser = mutableListOf()

        val linearLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val itemDecoration = DividerItemDecoration(this, RecyclerView.VERTICAL)
        recyclerView.addItemDecoration(itemDecoration)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = rcvUserAdapter

        rcvUserAdapter.setData(listUser)
        /* ******************************************************** */

        loadData()

        set.setOnClickListener {
            addUser()
        }

        deleteAll.setOnClickListener {
            UserDatabase.getInstance(this).userDAO().deleteAllUser()
            loadData()
        }

        search.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchUser()
                }
                return false
            }

        })

    }

    private fun searchUser() {
        val key: String = search.text.toString().trim()
        listUser = UserDatabase.getInstance(this).userDAO().searchUser(key)
        rcvUserAdapter.setData(listUser)
        closeKeyboard()
    }

    private fun addUser() {
        val userName: String = name.text.toString().trim()
        val userAge: String = age.text.toString().trim()

        if (userName.isEmpty() || userAge.isEmpty()) {
            return
        }

        /* set room database */
        val user = User(0, userName, userAge.toInt())
        val userMasterUser = MasterUser(0, userName, userAge.toInt())

        if (!isUserExist(user)) {
            Toast.makeText(this, "User exist", Toast.LENGTH_SHORT).show()
            return
        }

        UserDatabase.getInstance(this).userDAO().insertUser(user)
        UserDatabase.getInstance(this).userDAO2().insertUser23(userMasterUser)
        Toast.makeText(this, "Add Ok", Toast.LENGTH_LONG).show()

        /* ***************** */
        name.setText("")
        age.setText("")
        closeKeyboard()

        loadData()
    }


    private fun isUserExist(user: User): Boolean {
        val list: MutableList<User> = UserDatabase.getInstance(this).userDAO().checkUser(user.name)
        return list.isNullOrEmpty()
    }

    private fun loadData() {
        listUser = UserDatabase.getInstance(this).userDAO().getListUser()
        rcvUserAdapter.setData(listUser)
    }

    private fun closeKeyboard() {
        // this will give us the view
        // which is currently focus
        // in this layout
        val view = this.currentFocus

        // if nothing is currently
        // focus then this will protect
        // the app from crash
        if (view != null) {

            // now assign the system
            // service to InputMethodManager
            val manager = this
                .getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}