package com.azachos.movieapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShowsEntity: TvShowsEntity)

    @Query("SELECT * FROM tv_shows_table ORDER BY id ASC")
    fun readTvShows() : Flow<List<TvShowsEntity>>
}