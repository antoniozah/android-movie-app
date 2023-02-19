package com.azachos.movieapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.azachos.movieapp.models.TvShow
import com.azachos.movieapp.util.Constants.Companion.TV_SHOWS_TABLE

@Entity(tableName = TV_SHOWS_TABLE)
class TvShowsEntity(var tvShows: List<TvShow>) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}