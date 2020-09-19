package com.truetask.games.model

import com.truetask.games.api.GameResult
import com.truetask.games.api.GamesResponse
import com.truetask.games.db.GameEntity

object GamesConverter {

    fun fromDatabase(entity: GameEntity): Game =
        Game(
            gameId = entity.gameId,
            name = entity.name,
            releaseDate = entity.releasedDate,
            imageUrl = entity.logoUrl,
            rating = entity.rating,
            ratingsCount = entity.ratingsCount,
            maxRating = entity.maxRating,
            screenshots = entity.screenshots.split(','),
            category = entity.category
        )

    fun toDatabase(game: Game): GameEntity =
        GameEntity(
            gameId = game.gameId,
            name = game.name,
            releasedDate = game.releaseDate,
            logoUrl = game.imageUrl,
            rating = game.rating,
            ratingsCount = game.ratingsCount,
            maxRating = game.maxRating,
            screenshots = game.screenshots.joinToString(),
            category = game.category
        )

    fun fromNetwork(category: GameCategory, response: GamesResponse): GameData =
        GameData(
            hasNext = response.next != null,
            games = fromNetwork(category, response.results)
        )

    private fun fromNetwork(category: GameCategory, results: List<GameResult>): List<Game> =
        results.map { result ->
            Game(
                gameId = result.gameId,
                name = result.name,
                releaseDate = result.released.orEmpty(),
                imageUrl = result.imageUrl.orEmpty(),
                rating = result.rating,
                ratingsCount = result.ratingsCount,
                maxRating = result.ratingTop,
                screenshots = result.screenshots.map { it.imageUrl },
                category = category
            )
        }
}