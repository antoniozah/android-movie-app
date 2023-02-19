package com.azachos.movieapp.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Network(
    @SerializedName("country")
    val country: @RawValue Country,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("officialSite")
    val officialSite: String?
) : Parcelable