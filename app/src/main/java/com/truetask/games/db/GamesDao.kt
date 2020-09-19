package com.truetask.games.db

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.truetask.games.model.GameCategory

@Dao
interface GamesDao {

    @Query("SELECT * FROM GAME_TABLE WHERE COLUMN_CATEGORY = :category")
    fun getByCategory(category: GameCategory): DataSource.Factory<Int, GameEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(games: List<GameEntity>)
}