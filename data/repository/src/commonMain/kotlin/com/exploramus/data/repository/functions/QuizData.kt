package com.exploramus.data.repository.functions

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.core.models.Country
import com.exploramus.core.models.FlashcardConfig
import com.exploramus.core.models.isValidForQuiz
import com.exploramus.data.repository.Repository

suspend fun Repository.getQuizCountriesBySection(sectionId: String): List<Country> =
    getCountriesBySectionId(sectionId).filter { it.isValidForQuiz }

suspend fun Repository.getQuizCountriesAll(): List<Country> =
    getAllCountries().filter { it.isValidForQuiz }

suspend fun Repository.getQuizCountriesFavorites(): List<Country> =
    getFavorites().filter { it.isValidForQuiz }

suspend fun Repository.getQuizCountriesCountBySection(sectionId: String): Int =
    getQuizCountriesBySection(sectionId).size

suspend fun Repository.getQuizCountriesCountAll(): Int =
    getQuizCountriesAll().size

suspend fun Repository.getQuizCountriesCountFavorites(): Int =
    getQuizCountriesFavorites().size

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

fun Repository.getChoiceQuizPrimarySecondaryConfig(): ChoiceQuizConfig {
    return ChoiceQuizConfig(
        studyTarget = localSettings.choiceQuizPrimarySecondaryTarget,
        quizLimit = localSettings.choiceQuizLimit.takeIf { it != -1 }
    )
}

fun Repository.updateChoiceQuizPrimarySecondaryConfig(config: ChoiceQuizConfig) {
    localSettings.choiceQuizPrimarySecondaryTarget = config.studyTarget
    localSettings.choiceQuizLimit = config.quizLimit ?: -1
}

fun Repository.getChoiceQuizImagePrimaryConfig(): ChoiceQuizConfig {
    return ChoiceQuizConfig(
        studyTarget = localSettings.choiceQuizImagePrimaryTarget,
        quizLimit = localSettings.choiceQuizLimit.takeIf { it != -1 }
    )
}

fun Repository.updateChoiceQuizImagePrimaryConfig(config: ChoiceQuizConfig) {
    localSettings.choiceQuizImagePrimaryTarget = config.studyTarget
    localSettings.choiceQuizLimit = config.quizLimit ?: -1
}

fun Repository.getChoiceQuizNavigationMode(): ChoiceQuizNavigationMode {
    return localSettings.choiceQuizNavigationMode
}

fun Repository.updateChoiceQuizNavigationMode(mode: ChoiceQuizNavigationMode) {
    localSettings.choiceQuizNavigationMode = mode
}
