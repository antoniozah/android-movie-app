package com.azachos.movieapp.data.database

import androidx.room.TypeConverter
import com.azachos.movieapp.models.TvShow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TvShowsTypeConverter {

    private var gson = Gson()

    @TypeConverter
    fun dataModelToString(tvShows: List<TvShow>) : String {
        return gson.toJson(tvShows)
    }

    @TypeConverter
    fun stringToDataModel(data: String) : List<TvShow> {
        val listType = object : TypeToken<List<TvShow>>() {}.type
        return gson.fromJson(data, listType)
    }
}