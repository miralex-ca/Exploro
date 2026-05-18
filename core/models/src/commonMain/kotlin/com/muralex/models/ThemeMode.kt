package com.muralex.models

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM;

    companion object {
        val DEFAULT = LIGHT
    }
}