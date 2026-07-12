package com.exploramus.data.repository.functions

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.data.repository.Repository

fun Repository.getChoiceQuizPrimarySecondaryConfig(): ChoiceQuizConfig {
    return ChoiceQuizConfig(
        studyTarget = localSettings.choiceQuizPrimarySecondaryTarget,
        quizLimit = localSettings.choiceQuizPrimarySecondaryLimit.takeIf { it != -1 }
    )
}

fun Repository.updateChoiceQuizPrimarySecondaryConfig(config: ChoiceQuizConfig) {
    localSettings.choiceQuizPrimarySecondaryTarget = config.studyTarget
    localSettings.choiceQuizPrimarySecondaryLimit = config.quizLimit ?: -1
}

fun Repository.getChoiceQuizImagePrimaryConfig(): ChoiceQuizConfig {
    return ChoiceQuizConfig(
        studyTarget = localSettings.choiceQuizImagePrimaryTarget,
        quizLimit = localSettings.choiceQuizImagePrimaryLimit.takeIf { it != -1 }
    )
}

fun Repository.updateChoiceQuizImagePrimaryConfig(config: ChoiceQuizConfig) {
    localSettings.choiceQuizImagePrimaryTarget = config.studyTarget
    localSettings.choiceQuizImagePrimaryLimit = config.quizLimit ?: -1
}
