package com.truetask.games.domain.paging

import androidx.paging.PagedList
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

class MultiPagingManager(
    boundaryCallbackFactory: BoundaryCallbackFactory
) {

    private val gamesBehaviorProcessor = BehaviorProcessor.create<Pair<GameCategory, PagedList<Game>>>()
    private val stateBehaviorProcessor = BehaviorProcessor.create<Pair<GameCategory, PagingState>>()

    private val boundaries = boundaryCallbackFactory.create()

    fun loadAll() {
        boundaries.forEach { boundary ->
            boundary.load()
            boundary.onGamesUpdated { category, games ->
                gamesBehaviorProcessor.offer(category to games)
            }

            boundary.onStateUpdated { category, state ->
                stateBehaviorProcessor.offer(category to state)
            }
        }
    }

    fun getGamesFlowable(): Flowable<Pair<GameCategory, PagedList<Game>>> =
        gamesBehaviorProcessor

    fun getStateFlowable(): Flowable<Pair<GameCategory, PagingState>> =
        stateBehaviorProcessor

    fun refresh(category: GameCategory) {
        val boundary = boundaries.find { category == it.category } ?: return
        boundary.refresh()
    }

    fun clear() {
        boundaries.forEach { it.clear() }
    }
}