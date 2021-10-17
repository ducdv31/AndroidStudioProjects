package com.example.myhome.ui.view.fragment.main

import android.content.Intent
import android.view.View
import com.example.myhome.BaseFragment
import com.example.myhome.R
import com.example.myhome.ui.view.activity.movie.MoviesActivity
import com.example.myhome.ui.view.activity.music.MusicActivity

class RelaxFragment : BaseFragment() {

    private lateinit var lnMusic: View
    private lateinit var lnMovie: View

    override fun getLayout(): Int {
        return R.layout.fragment_relax
    }

    override fun initVar(rootView: View) {
        lnMusic = rootView.findViewById(R.id.ln_music)
        lnMovie = rootView.findViewById(R.id.ln_movie)
    }

    override fun initListener() {
        lnMusic.setOnClickListener {
            val intent = Intent(requireContext(), MusicActivity::class.java)
            startActivity(intent)
        }
        lnMovie.setOnClickListener {
            val intent = Intent(requireContext(), MoviesActivity::class.java)
            startActivity(intent)
        }
    }

    override fun handleLogic() {
    }
}