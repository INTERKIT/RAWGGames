package com.truetask.games.domain.paging.boundary

import androidx.paging.PagedList
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import com.truetask.games.domain.paging.PagingState

interface BoundaryCallbackContract {
    val category: GameCategory
    fun load()
    fun refresh()
    fun onGamesUpdated(callback: (GameCategory, PagedList<Game>) -> Unit)
    fun onStateUpdated(callback: (GameCategory, PagingState) -> Unit)
    fun clear()

    companion object {
        const val DEBOUNCE_TIMEOUT_MS = 200L
        const val PAGE_SIZE = 10
    }
}