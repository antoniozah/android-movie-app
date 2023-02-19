package com.azachos.movieapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.azachos.movieapp.data.database.TvShowsEntity
import com.azachos.movieapp.models.TvShow
import com.azachos.movieapp.util.NetworkResult

class TvShowsBinding {

    companion object {

        @BindingAdapter("readApiResponse", "readDatabase", requireAll = true)
        @JvmStatic
        fun errorImageViewVisibility(
            imageView: ImageView,
            apiResponse: NetworkResult<List<TvShow>>?,
            database: List<TvShowsEntity>?
        ) {
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                imageView.visibility = View.VISIBLE
            } else if(apiResponse is NetworkResult.Loading) {
                imageView.visibility = View.INVISIBLE
            } else if(apiResponse is NetworkResult.Success) {
                imageView.visibility = View.INVISIBLE
            }
        }

        @BindingAdapter("readApiResponseText", "readDatabaseText", requireAll = true)
        @JvmStatic
        fun errorTextViewVisibility(
            textView: TextView,
            apiResponse: NetworkResult<List<TvShow>>?,
            database: List<TvShowsEntity>?
        ) {
            if(apiResponse is NetworkResult.Error && database.isNullOrEmpty()) {
                textView.visibility = View.VISIBLE
                textView.text = apiResponse.message.toString()
            } else if(apiResponse is NetworkResult.Loading) {
                textView.visibility = View.INVISIBLE
            } else if(apiResponse is NetworkResult.Success) {
                textView.visibility = View.INVISIBLE
            }
        }
    }
}