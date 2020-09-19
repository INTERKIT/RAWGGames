package com.truetask.games.repository

import androidx.paging.DataSource
import com.truetask.games.api.GamesApi
import com.truetask.games.db.GamesDao
import com.truetask.games.model.Game
import com.truetask.games.model.GameCategory
import com.truetask.games.model.GameData
import com.truetask.games.model.GamesConverter
import io.reactivex.Completable
import io.reactivex.Single

class GamesRepositoryImpl(
    private val gamesApi: GamesApi,
    private val gamesDao: GamesDao
) : GamesRepository {

    override fun loadGames(page: Int, size: Int, category: GameCategory): Single<GameData> {
        val request = when (category) {
            GameCategory.DEVELOPER -> gamesApi.getGames(page, size, developers = category.stringValue)
            GameCategory.PUBLISHER -> gamesApi.getGames(page, size, publisher = category.stringValue)
            GameCategory.GENRE -> gamesApi.getGames(page, size, genre = category.stringValue)
        }
        return request.map { GamesConverter.fromNetwork(category, it) }
    }

    override fun insert(games: List<Game>): Completable = Completable.fromAction {
        val entities = games.map { GamesConverter.toDatabase(it) }
        gamesDao.insert(entities)
    }

    override fun getByCategory(category: GameCategory): DataSource.Factory<Int, Game> =
        gamesDao.getByCategory(category).mapByPage { entities ->
            entities.map { GamesConverter.fromDatabase(it) }
        }
}