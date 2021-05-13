package com.example.myroom.activityuserpermission

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myroom.R
import com.example.myroom.activitymain.MainActivity
import com.example.myroom.activityuserpermission.adapter.RCVUserPermissionAdapter
import com.example.myroom.activityuserpermission.fragment.ListUserPermissionFragment
import com.example.myroom.activityuserpermission.model.PermDataWithImg
import com.example.myroom.activityuserpermission.model.UserPermissionImg
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityUserPermission : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_permission)

        val actionBar = supportActionBar
        actionBar?.title = "Permission manager"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_user_permission, ListUserPermissionFragment())
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return true
    }

    fun getUserPermissionData(
        rcvUserPermissionAdapter: RCVUserPermissionAdapter
    ) {
//        val list: MutableList<UserPermission> = mutableListOf()
        FirebaseDatabase.getInstance().reference
            .child(MainActivity.PARENT_PERMISSION_CHILD)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.hasChildren()) {
                        /* get list id */
                        val list: MutableList<UserPermissionImg> = mutableListOf()
                        for (snap1: DataSnapshot in snapshot.children) {
                            /* id = snap1.key */
                            val id = snap1.key
                            val permData: PermDataWithImg? =
                                snap1.getValue(PermDataWithImg::class.java)
                            permData?.let {
                                when {
                                    id != null && permData.username != null && permData.email != null && permData.perm != null && permData.uri != null -> {
                                        list.add(
                                            UserPermissionImg(
                                                id,
                                                permData.uri,
                                                permData.username,
                                                permData.email,
                                                permData.perm
                                            )
                                        )
                                    }
                                    id != null && permData.username != null && permData.email != null && permData.perm == null && permData.uri != null -> {
                                        list.add(
                                            UserPermissionImg(
                                                id,
                                                permData.uri,
                                                permData.username,
                                                permData.email,
                                                99
                                            )
                                        )
                                    }
                                    else -> return
                                }
                            }

                        }
                        /* set list user */
                        list.sortBy { userPermissionImg -> userPermissionImg.perm }
                        rcvUserPermissionAdapter.setData(list)
                    }

                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}