package com.example.myhome.historyactivity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myhome.R
import com.example.myhome.historyactivity.fragment.HistoryDhtFragment
import com.example.myhome.historyactivity.fragment.HistoryHomeFragment
import java.io.Serializable

class HistoryActivity : AppCompatActivity() {

    companion object {
        const val DATA_DATE_ARGS = "data date"
    }

    private var actionBar: ActionBar? = null
    private val backStack = "Add back stack"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_history, HistoryHomeFragment())
        fragmentTransaction.commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        supportFragmentManager.popBackStack()
    }

    fun setTitleActionBar(title: String? = null, icon: Int? = null) {
        title?.let {
            actionBar?.title = title
        }
        icon?.let {
            actionBar?.setDisplayShowHomeEnabled(true)
            actionBar?.setDisplayUseLogoEnabled(true)
            actionBar?.setLogo(it)
        }
    }

    fun gotoFragment(fragment: Fragment, data: Any?, popBackStack: Boolean) {
        val fg = supportFragmentManager.beginTransaction()
        fg.replace(R.id.frame_history, fragment)
        data?.let {
            val bundle = Bundle()
            bundle.putSerializable(DATA_DATE_ARGS, data as Serializable?)
            fragment.arguments = bundle
        }
        if (popBackStack) {
            fg.addToBackStack(backStack)
        }
        fg.commit()
    }
}