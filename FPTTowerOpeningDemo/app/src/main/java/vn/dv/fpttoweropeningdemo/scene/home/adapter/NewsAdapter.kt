package vn.dv.fpttoweropeningdemo.scene.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import vn.dv.fpttoweropeningdemo.databinding.ItemNewsHomeBinding
import vn.dv.fpttoweropeningdemo.scene.home.model.NewsModel

class NewsAdapter(
    private val context: Context?
) : RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    private var listNews: MutableList<NewsModel> = mutableListOf()

    fun setData(list: MutableList<NewsModel>) {
        this.listNews = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        val binding =
            ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsVH(binding)
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val itemNews = listNews[position]
        holder.bindData(itemNews)
    }

    override fun getItemCount(): Int = listNews.size

    inner class NewsVH(
        private val binding: ItemNewsHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(newsModel: NewsModel) {
            context?.let {
                Glide.with(it)
                    .load(newsModel.imgNews)
                    .into(binding.imgNews)
            }
            binding.titleNews.text = newsModel.titleNews
        }
    }
}