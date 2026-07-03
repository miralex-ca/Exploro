package com.exploramus.data.repository.functions

import com.exploramus.data.repository.Repository


suspend fun Repository.getFavorites() = withRepoContext {
    localDb.getFavorites()
}

suspend fun Repository.isFavorite(code: String):  Boolean = withRepoContext {
    localDb.isFavorite(code)
}

suspend fun Repository.removeFavorite(code: String) = withRepoContext {
    localDb.removeFavorite(code)
}

suspend fun Repository.addFavorite(code: String) = withRepoContext {
    localDb.addFavorite(code)
}

suspend fun Repository.getFavoritesCount() = withRepoContext {
    localDb.getFavoritesCount()
}

