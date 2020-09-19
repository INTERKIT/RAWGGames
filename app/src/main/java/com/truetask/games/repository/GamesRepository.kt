package com.truetask.games.repository

import androidx.paging.DataSource
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import com.truetask.games.model.GameData
import io.reactivex.Completable
import io.reactivex.Single

interface GamesRepository {
    fun loadGames(page: Int, size: Int, category: GameCategory): Single<GameData>
    fun getByCategory(category: GameCategory): DataSource.Factory<Int, Game>
    fun insert(games: List<Game>): Completable
}

