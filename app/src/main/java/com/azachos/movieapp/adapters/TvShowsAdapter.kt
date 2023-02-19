package com.azachos.movieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.azachos.movieapp.databinding.TvShowItemLayoutBinding
import com.azachos.movieapp.models.TvShow
import com.azachos.movieapp.util.TvShowsDiffUtil

class TvShowsAdapter : RecyclerView.Adapter<TvShowsAdapter.MyViewHolder>() {

    private var tvShows = emptyList<TvShow>()

    class MyViewHolder(private val binding: TvShowItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(tvShow: TvShow) {
                binding.tvShow = tvShow

                //updates layout whenever a change in our data
                binding.executePendingBindings()
            }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = TvShowItemLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return tvShows.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentTvShow = tvShows[position]
        holder.bind(currentTvShow)
    }

    fun setData(newData: List<TvShow>) {
        val tvShowDiffUtil = TvShowsDiffUtil(tvShows, newData)
        val diffUtilResult = DiffUtil.calculateDiff(tvShowDiffUtil)
        tvShows = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }

}