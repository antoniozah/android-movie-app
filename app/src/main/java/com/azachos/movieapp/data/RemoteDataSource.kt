package com.azachos.movieapp.data

import com.azachos.movieapp.data.network.TvShowsApi
import com.azachos.movieapp.models.TvShow
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val tvShowsApi: TvShowsApi) {

    suspend fun getShows() : Response<List<TvShow>> {
        return tvShowsApi.getShows()
    }
}