package com.azachos.movieapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [TvShowsEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(TvShowsTypeConverter::class)
abstract class TvShowsDatabase : RoomDatabase() {

    abstract fun tvShowsDao() : TvShowsDao
}