package com.exploramus.data.repository.sources.localsettings

import com.exploramus.core.models.ChoiceQuizNavigationMode
import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.FlashcardStudyTarget
import com.russhwolf.settings.Settings
import com.russhwolf.settings.boolean
import com.russhwolf.settings.int
import com.russhwolf.settings.long
import com.russhwolf.settings.string

class MySettings (s : Settings) {
    var dataCacheTimestamp by s.long(defaultValue = 0)
    var apiSyncTimestamp by s.long(defaultValue = 0)
    var savedLevel1URI by s.string(defaultValue = "home:nul")
    var dbVersion by s.long(defaultValue = 1)
    var shouldForceDbUpdate by s.boolean(defaultValue = false)
    var themeModeId by s.int(defaultValue = 0)
    var favoriteSwipeEnabled by s.boolean(defaultValue = true)
    var flashcardsRevealEnabled by s.boolean(defaultValue = false)
    var flashcardsShuffleEnabled by s.boolean(defaultValue = false)

    private var flashcardStudyTargetId by s.int(defaultValue = FlashcardStudyTarget.DEFAULT.id)
    private var choiceQuizPrimarySecondaryTargetId by s.int(defaultValue = ChoiceQuizStudyTarget.PRIMARY_SECONDARY.id)
    private var choiceQuizImagePrimaryTargetId by s.int(defaultValue = ChoiceQuizStudyTarget.IMAGE_PRIMARY.id)
    private var choiceQuizNavigationModeId by s.int(defaultValue = ChoiceQuizNavigationMode.MANUAL.id)

    var choiceQuizLimit by s.int(defaultValue = -1)

    var flashcardStudyTarget: FlashcardStudyTarget
        get() = FlashcardStudyTarget.fromId(flashcardStudyTargetId)
        set(value) { flashcardStudyTargetId = value.id }

    var choiceQuizPrimarySecondaryTarget: ChoiceQuizStudyTarget
        get() = ChoiceQuizStudyTarget.fromId(choiceQuizPrimarySecondaryTargetId, ChoiceQuizStudyTarget.PRIMARY_SECONDARY)
        set(value) { choiceQuizPrimarySecondaryTargetId = value.id }

    var choiceQuizImagePrimaryTarget: ChoiceQuizStudyTarget
        get() = ChoiceQuizStudyTarget.fromId(choiceQuizImagePrimaryTargetId, ChoiceQuizStudyTarget.IMAGE_PRIMARY)
        set(value) { choiceQuizImagePrimaryTargetId = value.id }

    var choiceQuizNavigationMode: ChoiceQuizNavigationMode
        get() = ChoiceQuizNavigationMode.fromId(choiceQuizNavigationModeId, ChoiceQuizNavigationMode.MANUAL)
        set(value) { choiceQuizNavigationModeId = value.id }
}