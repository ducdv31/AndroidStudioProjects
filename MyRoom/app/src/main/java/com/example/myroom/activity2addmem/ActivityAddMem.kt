package com.example.myroom.activity2addmem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myroom.R
import com.example.myroom.activity2addmem.unknowuser.ListAddMemFragment
import com.example.myroom.activity2addmem.unknowuser.SetUserFragment
import com.example.myroom.activity2addmem.unknowuser.model.UserID

class ActivityAddMem : AppCompatActivity() {

    companion object{
        const val DATA_SEND_TO_SET_USER = "User id send from List add mem to Set Data User"
        const val BACK_STACK_LIST_ADD_MEM = "Add list add mem to back stack"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mem)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.title = "Add user"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_add_mem_list, ListAddMemFragment())
        fragmentTransaction.commit()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return true
    }

    fun gotoSetUserFragment(userID: UserID){
        val fragmentTransaction:FragmentTransaction =  supportFragmentManager.beginTransaction()
        /* send data to fragment */
        val setUserFragment:SetUserFragment = SetUserFragment()
        val bundle:Bundle = Bundle()
        bundle.putSerializable(DATA_SEND_TO_SET_USER, userID)
        setUserFragment.arguments = bundle
        /* ok set data */
        fragmentTransaction.replace(R.id.frame_add_mem_list, setUserFragment)
        fragmentTransaction.addToBackStack(BACK_STACK_LIST_ADD_MEM)
        fragmentTransaction.commit()

    }
}