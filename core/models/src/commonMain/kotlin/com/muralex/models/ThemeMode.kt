package com.muralex.models

enum class ThemeMode(
    val id: Int
) {
    LIGHT(0),
    DARK(1),
    SYSTEM(2);

    companion object {
        val DEFAULT = SYSTEM

        fun fromId(id: Int): ThemeMode {
            return entries.firstOrNull { it.id == id } ?: DEFAULT
        }

        fun byName(name: String): ThemeMode {
            return entries.firstOrNull { it.name.equals(name, ignoreCase = true) } ?: DEFAULT
        }
    }
}