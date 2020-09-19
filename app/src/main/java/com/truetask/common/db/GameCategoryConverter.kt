package com.truetask.common.db

import androidx.room.TypeConverter
import com.truetask.games.model.GameCategory

class GameCategoryConverter {

    @TypeConverter
    fun fromStringValue(stringValue: String): GameCategory = GameCategory[stringValue]

    @TypeConverter
    fun toStringValue(category: GameCategory): String = category.stringValue
}
