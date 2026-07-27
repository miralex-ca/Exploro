package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.core.models.ChoiceQuizStudyTarget
import com.exploramus.core.models.Country
import com.exploramus.shared.viewmodel.core.ScreenState
import com.exploramus.shared.viewmodel.screens.quizzes.common.DistractorPoolProvider
import com.exploramus.shared.viewmodel.screens.quizzes.common.SameListDistractorPoolProvider

data class ChoiceQuizScreenState(
    val isLoading: Boolean = false,
    val screenTitle: String = "",
    val quiz: ChoiceQuizState = ChoiceQuizState(),
    val isFinished: Boolean = false,
    val totalRestartEvent: Int = 0,
    val allCountries: List<Country> = emptyList(),
    val studyTarget: ChoiceQuizStudyTarget = ChoiceQuizStudyTarget.PRIMARY_SECONDARY,
    val quizLimit: Int? = null,
    val distractorPoolProvider: DistractorPoolProvider = SameListDistractorPoolProvider(emptyList()),
) : ScreenState
