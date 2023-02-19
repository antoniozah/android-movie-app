package com.azachos.movieapp.ui.fragments.showDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.navArgs
import coil.load
import com.azachos.movieapp.R
import com.azachos.movieapp.databinding.FragmentTvShowDetailBinding
import com.azachos.movieapp.models.TvShow
import com.azachos.movieapp.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import org.jsoup.Jsoup

@AndroidEntryPoint
class TvShowDetailFragment : Fragment() {

    private lateinit var binding: FragmentTvShowDetailBinding
    private val args: TvShowDetailFragmentArgs by navArgs()
    private lateinit var receivedData: TvShow
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragments
        binding = FragmentTvShowDetailBinding.inflate(inflater, container, false)
        retrieveData()
        setDetailsToolbarTitle()
        setDataToViews()
        return binding.root
    }

    private fun retrieveData() {
        receivedData = args.singleTvShow
    }

    private fun setDataToViews() {
        binding.apply {
            tvShowDetailTitle.text = receivedData.name
            tvShowDetailSummary.text = Jsoup.parse(receivedData.summary).text()
            tvShowDetailRatingBar.rating = receivedData.rating.average.toFloat()
            tvShowDetailRatingText.text = receivedData.rating.average.toString()
            tvShowDetailGenres.text = receivedData.genres.joinToString(separator = ", ")
            tvShowDetailPremiereContent.text = receivedData.premiered

            tvShowDetailNetworkName.text = receivedData.network?.name ?: ""
            tvShowDetailNetworkCountry.text = receivedData.network?.country?.name ?: ""
            setTvShowDetailImage()
        }
        receivedData.ended.let {
            val ended = it ?: "-"
            binding.tvShowDetailEndedContent.text = receivedData.ended
        }
    }

    private fun setTvShowDetailImage() {
        val image = receivedData.image.medium
        binding.tvShowDetailImage.load(image) {
            crossfade(600)
            error(R.drawable.broken_image)
        }
    }

    private fun setDetailsToolbarTitle() {
        activity?.let {mainActivity ->
            (mainActivity as MainActivity).supportActionBar?.title = receivedData.name
        }

    }

}