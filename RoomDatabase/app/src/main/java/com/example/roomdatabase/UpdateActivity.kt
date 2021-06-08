package com.example.roomdatabase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.roomdatabase.database.UserDatabase

class UpdateActivity : AppCompatActivity() {

    lateinit var username: EditText
    lateinit var userAge: EditText
    lateinit var update: Button

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        username = findViewById(R.id.et_name_update)
        userAge = findViewById(R.id.et_age_update)
        update = findViewById(R.id.update_data)

        user = intent.extras?.get("user") as User
        user?.let {
            username.setText(user!!.name)
            userAge.setText(user!!.age.toString())
        }

        update.setOnClickListener {
            updateUser(user!!)
        }
    }

    private fun updateUser(user: User) {
        val userName: String = username.text.toString().trim()
        val userAge2: String = userAge.text.toString().trim()

        if (userName.isEmpty() || userAge2.isEmpty()) {
            return
        }

        /*  update */
        user.name = userName
        user.age = userAge2.toInt()

        UserDatabase.getInstance(this).userDAO().updateUser(user)
        Toast.makeText(this, "update ok", Toast.LENGTH_SHORT).show()

        /* update to Main */
        val intentResult = Intent()
        setResult(Activity.RESULT_OK, intentResult)
        finish()
    }
}