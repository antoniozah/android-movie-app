package com.azachos.movieapp.data.network

import com.azachos.movieapp.models.TvShow
import retrofit2.Response
import retrofit2.http.GET

interface TvShowsApi {

    @GET("/shows")
    suspend fun getShows() : Response<List<TvShow>>
}