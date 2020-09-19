package com.truetask.games.domain.paging

import com.truetask.games.domain.GamesInteractor
import com.truetask.games.domain.paging.boundary.ActionGamesBoundaryCallback
import com.truetask.games.domain.paging.boundary.BoundaryCallbackContract
import com.truetask.games.domain.paging.boundary.DeveloperBoundaryCallback
import com.truetask.games.domain.paging.boundary.PublisherGamesBoundaryCallback

class BoundaryCallbackFactory(
    private val gamesInteractor: GamesInteractor
) {

    fun create(): List<BoundaryCallbackContract> = listOf(
        ActionGamesBoundaryCallback(gamesInteractor),
        PublisherGamesBoundaryCallback(gamesInteractor),
        DeveloperBoundaryCallback(gamesInteractor)
    )
}