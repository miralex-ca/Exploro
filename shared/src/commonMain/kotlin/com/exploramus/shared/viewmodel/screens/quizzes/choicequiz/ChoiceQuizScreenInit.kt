package com.exploramus.shared.viewmodel.screens.quizzes.choicequiz

import com.exploramus.data.repository.functions.getChoiceQuizImagePrimaryConfig
import com.exploramus.data.repository.functions.getChoiceQuizNavigationMode
import com.exploramus.data.repository.functions.getChoiceQuizPrimarySecondaryConfig
import com.exploramus.shared.viewmodel.core.CallOnInitValues
import com.exploramus.shared.viewmodel.core.ChoiceQuizScreenParams
import com.exploramus.shared.viewmodel.core.ScreenInitSettings
import com.exploramus.shared.viewmodel.core.StateManager
import com.exploramus.shared.viewmodel.screens.quizzes.common.ChoiceQuizDataSource
import com.exploramus.shared.viewmodel.screens.quizzes.common.ChoiceQuizSession
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.utils.QuizIdBuilder

fun StateManager.initChoiceQuizScreen(params: ChoiceQuizScreenParams) = ScreenInitSettings(
    title = params.screenTitle,
    initState = { _ -> ChoiceQuizScreenState(isLoading = true, screenTitle = params.screenTitle) },
    callOnInit = {
        val config = when (params.quizType) {
            QuizType.CHOICE_QUIZ_PRIMARY_SECONDARY -> dataRepository.getChoiceQuizPrimarySecondaryConfig()
            QuizType.CHOICE_QUIZ_IMAGE_PRIMARY -> dataRepository.getChoiceQuizImagePrimaryConfig()
            else -> null
        }

        val navigationMode = dataRepository.getChoiceQuizNavigationMode()
        val studyTarget = config?.studyTarget ?: params.quizType.toDefaultStudyTarget()
        val quizLimit = config?.quizLimit

        val session = ChoiceQuizSession.create(
            dataSource = ChoiceQuizDataSource.Default(dataRepository),
            sectionType = params.sectionType,
            sectionId = params.sectionId,
            studyTarget = studyTarget,
        )

        val quizItems = session.buildQuizItems(quizLimit)

        val quizId = QuizIdBuilder.build(
            sectionId = params.sectionId,
            sectionType = params.sectionType,
            quizType = params.quizType,
            studyTarget = studyTarget
        )

        val quizState = ChoiceQuizState(
            quizId = quizId,
            items = quizItems,
            config = ChoiceQuizConfigState(navigationMode = navigationMode)
        )

        updateScreen(ChoiceQuizScreenState::class) {
            it.copy(
                isLoading = false,
                screenTitle = params.screenTitle,
                quiz = quizState,
                quizLimit = quizLimit,
                session = session,
            )
        }
    },
    callOnInitAtEachNavigation = CallOnInitValues.DONT_CALL,
    clearStateCacheWhenScreenIsRemovedFromBackstack = true,
)
