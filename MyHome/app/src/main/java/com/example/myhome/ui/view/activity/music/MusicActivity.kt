package com.example.myhome.ui.view.activity.music

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhome.BaseActivity
import com.example.myhome.R
import com.example.myhome.data.model.music.TypeSongs
import com.example.myhome.ui.adapter.music.TypeMusicAdapter
import com.example.myhome.ui.viewmodel.music.MusicListViewmodel

class MusicActivity : BaseActivity() {

    private val TAG = MusicActivity::class.java.simpleName
    private val musicListViewmodel: MusicListViewmodel by viewModels()
    private lateinit var viewTopVN: View
    private lateinit var tvTopVN: TextView
    private lateinit var rvTopVN: RecyclerView

    private lateinit var viewTopAM: View
    private lateinit var tvTopAM: TextView
    private lateinit var rvTopAM: RecyclerView

    private lateinit var viewTopCA: View
    private lateinit var tvTopCA: TextView
    private lateinit var rvTopCA: RecyclerView

    private lateinit var viewTopKL: View
    private lateinit var tvTopKL: TextView
    private lateinit var rvTopKL: RecyclerView

    private lateinit var llmn1: LinearLayoutManager
    private lateinit var llmn2: LinearLayoutManager
    private lateinit var llmn3: LinearLayoutManager
    private lateinit var llmn4: LinearLayoutManager
    private lateinit var mTypeMusicAdapterVN: TypeMusicAdapter
    private lateinit var mTypeMusicAdapterAM: TypeMusicAdapter
    private lateinit var mTypeMusicAdapterCA: TypeMusicAdapter
    private lateinit var mTypeMusicAdapterKL: TypeMusicAdapter

    private lateinit var progressBar: ProgressBar
    private lateinit var viewAll: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        setTitleActionBar(getString(R.string.music))
        isShowUserImg(false)

        initVar()
        handleLogic()

        getData()

    }

    private fun initVar() {
        llmn1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        llmn2 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        llmn3 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        llmn4 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mTypeMusicAdapterVN = TypeMusicAdapter(this)
        mTypeMusicAdapterAM = TypeMusicAdapter(this)
        mTypeMusicAdapterCA = TypeMusicAdapter(this)
        mTypeMusicAdapterKL = TypeMusicAdapter(this)

        viewTopVN = findViewById(R.id.layout_top_vn)
        tvTopVN = viewTopVN.findViewById(R.id.name_top_music)
        rvTopVN = viewTopVN.findViewById(R.id.rv_type_song)
        rvTopVN.apply {
            layoutManager = llmn1
            adapter = mTypeMusicAdapterVN
        }
        viewTopVN.visibility = View.GONE

        viewTopAM = findViewById(R.id.layout_top_am)
        tvTopAM = viewTopAM.findViewById(R.id.name_top_music)
        rvTopAM = viewTopAM.findViewById(R.id.rv_type_song)
        rvTopAM.apply {
            layoutManager = llmn2
            adapter = mTypeMusicAdapterAM
        }
        viewTopAM.visibility = View.GONE

        viewTopCA = findViewById(R.id.layout_top_ca)
        tvTopCA = viewTopCA.findViewById(R.id.name_top_music)
        rvTopCA = viewTopCA.findViewById(R.id.rv_type_song)
        rvTopCA.apply {
            layoutManager = llmn3
            adapter = mTypeMusicAdapterCA
        }
        viewTopCA.visibility = View.GONE

        viewTopKL = findViewById(R.id.layout_top_kl)
        tvTopKL = viewTopKL.findViewById(R.id.name_top_music)
        rvTopKL = viewTopKL.findViewById(R.id.rv_type_song)
        rvTopKL.apply {
            layoutManager = llmn4
            adapter = mTypeMusicAdapterKL
        }
        viewTopKL.visibility = View.GONE

        progressBar = findViewById(R.id.progress_bar)
        viewAll =findViewById(R.id.layout_all)
    }

    private fun handleLogic() {
        musicListViewmodel.musicData.observe(this) { musicData ->
            musicData.songs?.topVN?.let {
                viewTopVN.visibility = View.VISIBLE
                tvTopVN.text = getString(R.string.top_vn)
                mTypeMusicAdapterVN.setData(it as MutableList<TypeSongs>)
            } ?: kotlin.run { viewTopVN.visibility = View.GONE }

            musicData.songs?.topAM?.let {
                viewTopAM.visibility = View.VISIBLE
                tvTopAM.text = getString(R.string.top_am)
                mTypeMusicAdapterAM.setData(it as MutableList<TypeSongs>)
            } ?: kotlin.run { viewTopAM.visibility = View.GONE }

            musicData.songs?.topCA?.let {
                viewTopCA.visibility = View.VISIBLE
                tvTopCA.text = getString(R.string.top_ca)
                mTypeMusicAdapterCA.setData(it as MutableList<TypeSongs>)
            } ?: kotlin.run { viewTopCA.visibility = View.GONE }

            musicData.songs?.topKL?.let {
                viewTopKL.visibility = View.VISIBLE
                tvTopKL.text = getString(R.string.top_kl)
                mTypeMusicAdapterKL.setData(it as MutableList<TypeSongs>)
            } ?: kotlin.run { viewTopKL.visibility = View.GONE }
        }
    }

    private fun getData() {
        showProgress(true)
        musicListViewmodel.getListSong(
            onSuccess = {
                showProgress(false)
            },
            onFailure = {
                showProgress(false)
            }
        )
    }

    private fun showProgress(isShow: Boolean) {
        if (isShow) {
            progressBar.visibility = View.VISIBLE
            viewAll.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            viewAll.visibility = View.VISIBLE
        }
    }
}