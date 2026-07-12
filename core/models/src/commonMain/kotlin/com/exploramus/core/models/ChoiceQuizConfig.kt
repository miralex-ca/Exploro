package com.exploramus.core.models

data class ChoiceQuizConfig(
    val studyTarget: ChoiceQuizStudyTarget,
    val quizLimit: Int? = null
)

enum class ChoiceQuizStudyTarget(val id: Int) {
    PRIMARY_SECONDARY(0),
    SECONDARY_PRIMARY(1),
    IMAGE_PRIMARY(2),
    PRIMARY_IMAGE(3);

    companion object {
        fun fromId(id: Int, default: ChoiceQuizStudyTarget): ChoiceQuizStudyTarget =
            entries.firstOrNull { it.id == id } ?: default
    }
}
