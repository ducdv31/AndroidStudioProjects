package com.example.myroom.activitylistmem

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.myroom.R
import com.example.myroom.activitylistmem.knowuser.DetailUserFragment
import com.example.myroom.activitylistmem.knowuser.ListUserFragment
import com.example.myroom.activitylistmem.knowuser.UpdateUserFragment
import com.example.myroom.activitylistmem.model.UserDetail

class ActivityListMem : AppCompatActivity() {

    companion object {
        const val USER_SEND_TAG = "User_detail"
        const val USER_SEND_TO_UPDATE = "User_update"
        const val BACK_STACK_LIST_USER = "List user to detail user"
        const val BACK_STACK_UPDATE_USER = "List user to update user"
    }

    var menuItem: MenuItem? = null
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_mem)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Members"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.frame_list_mem_act, ListUserFragment())
        fragmentTransition.commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list_mem, menu)
        menuItem = menu?.findItem(R.id.search_mem)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu?.findItem(R.id.search_mem)!!.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                ListUserFragment.rcvUserAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                ListUserFragment.rcvUserAdapter.filter.filter(newText)
                return false
            }

        })

        return true
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun gotoDetailUser(userDetail: UserDetail) {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        /* send data to fragment */
        val detailUserFragment: DetailUserFragment = DetailUserFragment()
        val bundle: Bundle = Bundle()
        bundle.putSerializable(USER_SEND_TAG, userDetail)
        detailUserFragment.arguments = bundle
        /* ok */
        fragmentTransition.replace(R.id.frame_list_mem_act, detailUserFragment)
        fragmentTransition.addToBackStack(BACK_STACK_LIST_USER)
        fragmentTransition.commit()

    }

    fun gotoUpdateUser(userDetail: UserDetail) {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        /* send data to fragment */
        val updateUserFragment: UpdateUserFragment = UpdateUserFragment()
        val bundle: Bundle = Bundle()
        bundle.putSerializable(USER_SEND_TO_UPDATE, userDetail)
        updateUserFragment.arguments = bundle
        /* ok */
        fragmentTransition.replace(R.id.frame_list_mem_act, updateUserFragment)
        fragmentTransition.addToBackStack(BACK_STACK_UPDATE_USER)
        fragmentTransition.commit()

    }

    fun closeKeyboard() {
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