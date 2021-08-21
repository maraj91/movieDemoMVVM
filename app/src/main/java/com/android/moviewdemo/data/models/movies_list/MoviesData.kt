package com.android.moviewdemo.data.models.movies_list


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesData(
    @SerializedName("actors")
    val cast: List<String>,
    @SerializedName("genres")
    val genres: List<String>,
    @SerializedName("ratings")
    val rating: List<Int>,
    @SerializedName("title")
    val title: String,
    @SerializedName("year")
    val year: String,
    @SerializedName("releaseDate")
    val releaseDate: String,
    @SerializedName("storyline")
    val storyLine: String,
    @SerializedName("posterurl")
    val posterUrl: String
):Parcelable