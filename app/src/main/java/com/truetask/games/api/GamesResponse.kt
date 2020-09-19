package com.truetask.games.api

import com.google.gson.annotations.SerializedName

data class GamesResponse(
    @SerializedName("seo_title")
    val title: String,
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String?,
    @SerializedName("previous")
    val previous: String?,
    @SerializedName("results")
    val results: List<GameResult>
)

data class GameResult(
    @SerializedName("id")
    val gameId: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("released")
    val released: String?,

    @SerializedName("background_image")
    val imageUrl: String?,

    @SerializedName("rating")
    val rating: Float,

    @SerializedName("rating_top")
    val ratingTop: Float,

    @SerializedName("ratings_count")
    val ratingsCount: Int,

    @SerializedName("short_screenshots")
    val screenshots: List<ScreenshotResponse>,

    @SerializedName("platforms")
    val platforms: List<PlatformDataResponse>

)

data class ScreenshotResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val imageUrl: String
)

data class PlatformDataResponse(
    @SerializedName("platform")
    val platform: PlatformResponse,

    @SerializedName("released_at")
    val releasedAt: String
)

data class PlatformResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("slug")
    val slug: String,

    @SerializedName("name")
    val name: String
)