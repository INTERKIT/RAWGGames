package com.truetask.games.model

enum class GameCategory(val stringValue: String) {
    DEVELOPER("valve-software"),
    GENRE("action"),
    PUBLISHER("electronic-arts");

    companion object {
        operator fun get(stringValue: String) =
            values().firstOrNull { it.stringValue == stringValue } ?: DEVELOPER
    }
}