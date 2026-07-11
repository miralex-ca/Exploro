package com.exploramus.data.repository.functions

import com.exploramus.core.models.FlashcardConfig
import com.exploramus.data.repository.Repository

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
