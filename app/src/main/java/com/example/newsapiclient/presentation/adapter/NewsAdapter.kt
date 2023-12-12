package com.example.newsapiclient.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapiclient.R
import com.example.newsapiclient.data.model.Article
import com.example.newsapiclient.databinding.NewsListItemBinding

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.viewholder>() {

    private val callback  = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,callback)




    inner class viewholder(val binding:NewsListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(article: Article){
            binding.tvTitle.text = article.title
            binding.tvDescription.text = article.description
            binding.tvSource.text = article.source!!.name
            binding.tvPublishedAt.text = article.publishedAt

            Glide.with(binding.ivArticleImage.context).load(article.urlToImage)
                .into(binding.ivArticleImage)

            binding.root.setOnClickListener{
                itemOnClickListener?.let {
                    it(article)
                }
            }

        }

    }

    private var itemOnClickListener  : ( (Article) -> Unit ) ? = null


    fun setOnItemClickListener(listener : (Article) ->Unit){
        itemOnClickListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val binding = NewsListItemBinding.inflate(LayoutInflater.from(parent.context)
        ,parent,false)
        return viewholder(binding)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val article = differ.currentList[position]
        holder.bind(article)

    }


}