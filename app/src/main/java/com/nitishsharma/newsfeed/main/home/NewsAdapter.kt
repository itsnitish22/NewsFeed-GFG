package com.nitishsharma.newsfeed.main.home

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.nitishsharma.newsfeed.api.models.Item
import com.nitishsharma.newsfeed.databinding.ItemNewsLargeBinding
import com.nitishsharma.newsfeed.databinding.ItemNewsSmallBinding
import com.nitishsharma.newsfeed.main.utils.Utility

class NewsAdapter(
    private val inflater: LayoutInflater,
    private val news: ArrayList<Item>,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //on createVH
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //if view type is first (top) news, then inflate the largeBinding, otherwise inflate smallBinding
        val binding = when (viewType) {
            FIRST -> ItemNewsLargeBinding.inflate(inflater, parent, false)
            NOT_FIRST -> ItemNewsSmallBinding.inflate(inflater, parent, false)
            else -> throw IllegalArgumentException("Invalid view type")
        }
        return when (viewType) {
            FIRST -> LargeNewsVH(binding as ItemNewsLargeBinding)
            NOT_FIRST -> SmallNewsVH(binding as ItemNewsSmallBinding)
            else -> EmptyViewHolder(binding.root)
        }
    }

    override fun getItemCount() = news.size

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val news = news[position]
        //binding news according to the received view type
        when (holder) {
            is LargeNewsVH -> {
                with(holder) {
                    binding.apply {
                        Glide.with(newsImage.context).load(news.enclosure.link)
                            .diskCacheStrategy( //set image with caching
                                DiskCacheStrategy.DATA
                            ).into(newsImage) //into sets up image in async fashion
                        newsTitle.text = news.title
                        datePub.text = Utility.formatDateTime(news.pubDate)
                    }
                }
            }

            is SmallNewsVH -> {
                with(holder) {
                    binding.apply {
                        Glide.with(newsImage.context).load(news.thumbnail)
                            .diskCacheStrategy(
                                DiskCacheStrategy.DATA
                            ).into(newsImage)
                        newsTitle.text = news.title
                        datePub.text = Utility.formatDateTime(news.pubDate)
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) FIRST else NOT_FIRST //if position == 0 view type should be first, else not first

    }

    companion object {
        const val FIRST = 0
        const val NOT_FIRST = 1
    }

    inner class LargeNewsVH(val binding: ItemNewsLargeBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class SmallNewsVH(val binding: ItemNewsSmallBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}