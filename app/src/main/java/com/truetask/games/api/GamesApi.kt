package com.truetask.games.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesApi {

    @GET("games")
    fun getGames(
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("developers") developers: String? = null,
        @Query("publishers") publisher: String? = null,
        @Query("genres") genre: String? = null
    ): Single<GamesResponse>
}