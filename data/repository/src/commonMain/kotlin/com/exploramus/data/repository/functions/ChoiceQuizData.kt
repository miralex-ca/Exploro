package com.exploramus.data.repository.functions

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.data.repository.Repository

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
