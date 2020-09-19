package com.truetask.games.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.truetask.games.db.GameEntity.Companion.TABLE_NAME
import com.truetask.games.model.GameCategory

@Entity(tableName = TABLE_NAME)
data class GameEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_GAME_ID)
    val gameId: Int,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String,

    @ColumnInfo(name = COLUMN_RELEASED)
    val releasedDate: String,

    @ColumnInfo(name = COLUMN_LOGO_URL)
    val logoUrl: String,

    @ColumnInfo(name = COLUMN_RATING)
    val rating: Float,

    @ColumnInfo(name = COLUMN_RATING_COUNT)
    val ratingsCount: Int,

    @ColumnInfo(name = COLUMN_RATING_MAX)
    val maxRating: Float,

    @ColumnInfo(name = COLUMN_SCREENSHOTS)
    val screenshots: String,

    @ColumnInfo(name = COLUMN_CATEGORY)
    val category: GameCategory
) {

    companion object {
        const val TABLE_NAME = "GAME_TABLE"
        const val COLUMN_GAME_ID = "COLUMN_GAME_ID"
        const val COLUMN_NAME = "COLUMN_NAME"
        const val COLUMN_RELEASED = "COLUMN_RELEASED"
        const val COLUMN_LOGO_URL = "COLUMN_LOGO_URL"
        const val COLUMN_RATING = "COLUMN_RATING"
        const val COLUMN_RATING_COUNT = "COLUMN_RATING_COUNT"
        const val COLUMN_RATING_MAX = "COLUMN_RATING_MAX"
        const val COLUMN_SCREENSHOTS = "COLUMN_SCREENSHOTS"
        const val COLUMN_CATEGORY = "COLUMN_CATEGORY"
    }
}