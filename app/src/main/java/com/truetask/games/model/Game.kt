package com.truetask.games.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Game(
    val gameId: Int,
    val name: String,
    val releaseDate: String,
    val imageUrl: String,
    val rating: Float,
    val ratingsCount: Int,
    val maxRating: Float,
    val screenshots: List<String>,
    val category: GameCategory
) : Parcelable