package com.azachos.movieapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class TvShow(
    @SerializedName("averageRuntime")
    val averageRuntime: Int,
    @SerializedName("ended")
    val ended: String?,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: @RawValue Image,
    @SerializedName("language")
    val language: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("network")
    val network: @RawValue Network?,
    @SerializedName("premiered")
    val premiered: String,
    @SerializedName("rating")
    val rating: @RawValue Rating,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated")
    val updated: Int,
) : Parcelable