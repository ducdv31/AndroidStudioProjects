package vn.dv.motionlayoutfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import vn.dv.motionlayoutfragment.fragment.FirstFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_main, FirstFragment())
            .commit()
    }
}