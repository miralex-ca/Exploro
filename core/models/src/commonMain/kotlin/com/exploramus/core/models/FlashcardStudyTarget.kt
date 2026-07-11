package com.exploramus.core.models

enum class FlashcardStudyTarget(val id: Int) {
    PRIMARY(0),
    SECONDARY(1),
    IMAGE(2);

    companion object {
        val DEFAULT = IMAGE

        fun fromId(id: Int): FlashcardStudyTarget =
            entries.firstOrNull { it.id == id } ?: DEFAULT
    }
}