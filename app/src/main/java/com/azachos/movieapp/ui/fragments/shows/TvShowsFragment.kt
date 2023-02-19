package com.azachos.movieapp.ui.fragments.shows

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.azachos.movieapp.MainViewModel
import com.azachos.movieapp.R
import com.azachos.movieapp.adapters.TvShowsAdapter
import com.azachos.movieapp.databinding.FragmentTvShowsBinding
import com.azachos.movieapp.util.NetworkResult
import com.azachos.movieapp.util.observeOnce
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val tvShowsAdapter by lazy { TvShowsAdapter() }
    private lateinit var binding: FragmentTvShowsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragments
        binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecyclerView()
        readDatabase()
        onSwipeRefresh()
        return  binding.root
    }

    private fun setupRecyclerView() {
        binding.tvShowsRecyclerView.apply {
            adapter = tvShowsAdapter
            layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun requestApiData() {
        Log.d("TvShows", "Internet Call")
        mainViewModel.getTvShows()
        mainViewModel.tvShowsResponse.observe(this.viewLifecycleOwner) { response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideLoader()
                    response.data?.let {tvShowsAdapter.setData(it)}
                    binding.tvShowsSwipeRefresh.isRefreshing = false
                }
                is NetworkResult.Error -> {
                    hideLoader()
                    binding.tvShowsSwipeRefresh.isRefreshing = false
                    loadCachedData()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showLoader()
                }
            }

        }
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readTvShows.observeOnce(viewLifecycleOwner) {database ->
                if(database.isNotEmpty()) {
                    Log.d("TvShows", "DATABASE Call")
                    tvShowsAdapter.setData(database[0].tvShows)
                    hideLoader()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun loadCachedData() {
        lifecycleScope.launch {
            mainViewModel.readTvShows.observe(viewLifecycleOwner) {database ->
                if(database.isNotEmpty()) {
                    tvShowsAdapter.setData(database[0].tvShows)
                }
            }
        }
    }

    private fun onSwipeRefresh() {
        binding.tvShowsSwipeRefresh.setOnRefreshListener {
            requestApiData()
        }
    }

    private fun showLoader() {
        binding.tvShowsLoader.visibility = View.VISIBLE
    }

    private fun hideLoader() {
        binding.tvShowsLoader.visibility = View.INVISIBLE
    }

}