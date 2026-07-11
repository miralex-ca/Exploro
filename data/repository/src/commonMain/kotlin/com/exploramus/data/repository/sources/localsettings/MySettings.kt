package com.exploramus.data.repository.sources.localsettings

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

    var flashcardStudyTarget: FlashcardStudyTarget
        get() = FlashcardStudyTarget.fromId(flashcardStudyTargetId)
        set(value) { flashcardStudyTargetId = value.id }
}