package com.azachos.movieapp.util

import androidx.recyclerview.widget.DiffUtil
import com.azachos.movieapp.models.TvShow

class TvShowsDiffUtil(private val oldList: List<TvShow>, private val newList: List<TvShow>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}