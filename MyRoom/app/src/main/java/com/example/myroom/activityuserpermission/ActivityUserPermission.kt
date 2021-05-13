package com.example.myroom.activityuserpermission

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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

    /* search user */
    lateinit var menuItem: MenuItem
    lateinit var searchView: SearchView
    /* **************************** */

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user_permission, menu)
        menuItem = menu?.findItem(R.id.search_user_permission)!!
        searchView = menu.findItem(R.id.search_user_permission)!!.actionView as SearchView

        val searchManager:SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                ListUserPermissionFragment.rcvUserPermissionAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ListUserPermissionFragment.rcvUserPermissionAdapter.filter.filter(newText)
                return false
            }

        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return true
    }

    override fun onBackPressed() {
        if (!searchView.isIconified){
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    fun getUserPermissionData(
        rcvUserPermissionAdapter: RCVUserPermissionAdapter
    ) {
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
                                    else -> {

                                    }
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