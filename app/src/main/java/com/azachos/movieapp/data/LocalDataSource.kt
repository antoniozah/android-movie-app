package com.azachos.movieapp.data

import com.azachos.movieapp.data.database.TvShowsDao
import com.azachos.movieapp.data.database.TvShowsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val tvShowsDao: TvShowsDao) {

    fun readDatabase() : Flow<List<TvShowsEntity>> {
        return tvShowsDao.readTvShows()
    }

    suspend fun insertTvShows(tvShowsEntity: TvShowsEntity) {
        tvShowsDao.insertTvShows(tvShowsEntity)
    }
}