package com.exploramus.shared.viewmodel.core

import com.exploramus.core.models.QuizItemStatus
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizType
import com.exploramus.shared.viewmodel.screens.quizzes.quizzeslist.QuizzesSectionType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenParams

@Serializable
@SerialName("DetailsPagerScreenParams")
data class DetailsPagerScreenParams(
    val countryCode: String? = null,
    val sectionId: String? = null,
    val screenTitle: String? = null
) : ScreenParams

@Serializable
@SerialName("FavoritesPagerScreenParams")
data class FavoritesPagerScreenParams(
    val countryCode: String? = null,
    val screenTitle: String? = null
) : ScreenParams

@Serializable
@SerialName("DetailsScreenParams")
data class DetailsScreenParams(
    val countryCode: String? = null,
    val screenTitle: String? = null
) : ScreenParams

@Serializable
@SerialName("ChoiceQuizScreenParams")
data class ChoiceQuizScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String,
    val quizType: QuizType,
) : ScreenParams

@Serializable
@SerialName("FlashcardScreenParams")
data class FlashcardScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String,
) : ScreenParams

@Serializable
@SerialName("GroupedItemsScreenParams")
data class GroupedItemsScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String? = null,
    val masteryStatus: QuizItemStatus? = null,
) : ScreenParams

@Serializable
@SerialName("QuizzesListScreenParams")
data class QuizzesListScreenParams(
    val sectionId: String,
    val sectionType: QuizzesSectionType,
    val screenTitle: String? = null
) : ScreenParams

@Serializable
@SerialName("SectionParams")
data class SectionParams(
    val continent: String,
    val screenTitle: String? = null
) : ScreenParams
