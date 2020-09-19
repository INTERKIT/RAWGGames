package com.truetask.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.truetask.common.db.RAWGDatabase.Companion.DATABASE_VERSION
import com.truetask.games.db.GameEntity
import com.truetask.games.db.GamesDao

@Database(
    version = DATABASE_VERSION,
    entities = [GameEntity::class],
    exportSchema = false
)
@TypeConverters(
    GameCategoryConverter::class
)
abstract class RAWGDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "RAWG_GAMES"
        const val DATABASE_VERSION = 1
    }

    abstract fun gamesDao(): GamesDao
}