package com.truetask.games.domain

import androidx.paging.DataSource
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import com.truetask.games.repository.GamesRepository
import io.reactivex.Single

class GamesInteractor(
    private val gamesRepository: GamesRepository
) {

    fun getByCategory(category: GameCategory): DataSource.Factory<Int, Game> =
        gamesRepository.getByCategory(category)

    fun loadGames(page: Int, size: Int, category: GameCategory): Single<Boolean> =
        gamesRepository.loadGames(page, size, category)
            .flatMap {
                gamesRepository.insert(it.games).toSingle { it.hasNext }
            }
}