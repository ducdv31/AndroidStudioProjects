package vn.dv.myvideo.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import vn.dv.myvideo.R
import vn.dv.myvideo.common.BaseActivity
import vn.dv.myvideo.databinding.ActivityMainBinding
import vn.dv.myvideo.view.main.fragment.MainFragment

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerMain, MainFragment())
            .commit()
    }
}