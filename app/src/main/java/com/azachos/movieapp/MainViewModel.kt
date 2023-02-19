package com.azachos.movieapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.azachos.movieapp.data.Repository
import com.azachos.movieapp.data.database.TvShowsEntity
import com.azachos.movieapp.models.TvShow
import com.azachos.movieapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    // Room Database
    val readTvShows : LiveData<List<TvShowsEntity>> = repository.local.readDatabase().asLiveData()

    private fun insertTvShows(tvShowsEntity: TvShowsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertTvShows(tvShowsEntity)
        }
    }

    // Retrofit
    val tvShowsResponse: MutableLiveData<NetworkResult<List<TvShow>>> = MutableLiveData()

    fun getTvShows() =
        viewModelScope.launch {
            getTvShowsSafeCall()
        }


    private suspend fun getTvShowsSafeCall() {
        tvShowsResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val response = repository.remote.getShows()
                tvShowsResponse.value = handleTvShowsResponse(response)

                val tvShows = tvShowsResponse.value?.data
                if(tvShows !== null) {
                    offlineCacheTvShows(tvShows)
                }
            } catch (e: Exception) {
                NetworkResult.Error("Tv Shows not found.", null)
            }
        } else {
            tvShowsResponse.value = NetworkResult.Error("No Internet Connection.")
        }
    }

    private fun offlineCacheTvShows(tvShows: List<TvShow>) {
        val tvShowsEntity = TvShowsEntity(tvShows)
        insertTvShows(tvShowsEntity)
    }

    private fun handleTvShowsResponse(response: Response<List<TvShow>>): NetworkResult<List<TvShow>> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout.", null)
            }
            response.body().isNullOrEmpty() -> {
                NetworkResult.Error("Tv Shows not found.", null)
            }
            response.isSuccessful -> {
                val tvShows = response.body()
                NetworkResult.Success(tvShows)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}