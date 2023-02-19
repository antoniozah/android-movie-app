package com.azachos.movieapp.bindingadapters

import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.azachos.movieapp.R
import com.azachos.movieapp.models.Network
import com.azachos.movieapp.models.Rating
import com.azachos.movieapp.models.TvShow
import com.azachos.movieapp.ui.fragments.shows.TvShowsFragmentDirections
import com.google.android.material.textview.MaterialTextView
import org.jsoup.Jsoup

class TvShowItemBinding {

    companion object {

        @BindingAdapter("onItemClick")
        @JvmStatic
        fun onItemClick(viewLayout: ConstraintLayout, tvShow: TvShow) {
            Log.d("TvShows", "CALLED!!!")
            viewLayout.setOnClickListener {
                try {
                    val action = TvShowsFragmentDirections.actionTvShowsFragmentToTvShowDetailFragment(tvShow)
                    viewLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("TvShows", e.message.toString())
                }
            }
        }

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(image: ImageView, url: String) {
            image.load(url) {
                crossfade(600)
                error(R.drawable.broken_image)
            }
        }

        @BindingAdapter("setTvShowRating")
        @JvmStatic
        fun setTvShowRating(textView: MaterialTextView, rating: Rating) {
            textView.text = rating.average.toString()
        }

        @BindingAdapter("setTvShowSummary")
        @JvmStatic
        fun setTvShowSummary(textView: MaterialTextView, summary: String) {
            textView.text = Jsoup.parse(summary).text()
        }

        @BindingAdapter("setTvShowNetwork")
        @JvmStatic
        fun setTvShowNetwork(textView: MaterialTextView, network: Network?) {
            textView.text = network?.name ?: ""
        }

    }
}