package com.exploramus.data.repository.functions

import com.exploramus.core.models.ChoiceQuizConfig
import com.exploramus.data.repository.Repository

fun Repository.getChoiceQuizPrimarySecondaryConfig(): ChoiceQuizConfig {
    return ChoiceQuizConfig(localSettings.choiceQuizPrimarySecondaryTarget)
}

fun Repository.updateChoiceQuizPrimarySecondaryConfig(config: ChoiceQuizConfig) {
    localSettings.choiceQuizPrimarySecondaryTarget = config.studyTarget
}

fun Repository.getChoiceQuizImagePrimaryConfig(): ChoiceQuizConfig {
    return ChoiceQuizConfig(localSettings.choiceQuizImagePrimaryTarget)
}

fun Repository.updateChoiceQuizImagePrimaryConfig(config: ChoiceQuizConfig) {
    localSettings.choiceQuizImagePrimaryTarget = config.studyTarget
}
