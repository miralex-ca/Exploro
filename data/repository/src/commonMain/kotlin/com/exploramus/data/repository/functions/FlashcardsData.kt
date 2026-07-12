package com.exploramus.data.repository.functions

import com.exploramus.core.models.Country
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.data.repository.Repository

suspend fun Repository.getFlashcardCountriesBySection(sectionId: String): List<Country> =
    getCountriesBySectionId(sectionId).filterValidForFlashcards()

suspend fun Repository.getFlashcardCountriesAll(): List<Country> =
    getAllCountries().filterValidForFlashcards()

suspend fun Repository.getFlashcardCountriesFavorites(): List<Country> =
    getFavorites().filterValidForFlashcards()

private fun List<Country>.filterValidForFlashcards(): List<Country> =
    filter { it.name.isNotBlank() && it.capital.isNotBlank() }

fun Repository.getFlashcardConfig(): FlashcardConfig {
    return FlashcardConfig(
        studyTarget = localSettings.flashcardStudyTarget,
        revealEnabled = localSettings.flashcardsRevealEnabled,
        shuffleEnabled = localSettings.flashcardsShuffleEnabled
    )
}

fun Repository.updateFlashcardConfig(config: FlashcardConfig) {
    localSettings.flashcardStudyTarget = config.studyTarget
    localSettings.flashcardsRevealEnabled = config.revealEnabled
    localSettings.flashcardsShuffleEnabled = config.shuffleEnabled
}
