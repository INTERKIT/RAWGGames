package com.truetask.games.model

import androidx.annotation.StringRes
import androidx.paging.PagedList
import com.truetask.games.domain.paging.PagingState

sealed class GamesType(
    open val title: Int,
    open val state: PagingState,
    open val games: PagedList<Game>?
) {
    data class Developer(
        @StringRes override val title: Int,
        override val state: PagingState = PagingState.Idle,
        override val games: PagedList<Game>? = null
    ) : GamesType(title, state, games)

    data class Genre(
        @StringRes override val title: Int,
        override val state: PagingState = PagingState.Idle,
        override val games: PagedList<Game>? = null
    ) : GamesType(title, state, games)

    data class Publisher(
        @StringRes override val title: Int,
        override val state: PagingState = PagingState.Idle,
        override val games: PagedList<Game>? = null
    ) : GamesType(title, state, games)

    fun isSame(category: GameCategory): Boolean =
        when (category) {
            GameCategory.DEVELOPER -> this is Developer
            GameCategory.GENRE -> this is Genre
            GameCategory.PUBLISHER -> this is Publisher
        }

    fun getCategory(): GameCategory =
        when (this) {
            is Developer -> GameCategory.DEVELOPER
            is Genre -> GameCategory.GENRE
            is Publisher -> GameCategory.PUBLISHER
        }
}