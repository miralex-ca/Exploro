package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import exploramus.composeapp.generated.resources.*
import org.jetbrains.compose.resources.stringResource

object Strings {
    val homeTitle @Composable get() = stringResource(Res.string.screen_home_title)
    val favoritesTitle @Composable get() = stringResource(Res.string.screen_favorites_title)
    val searchTitle @Composable get() = stringResource(Res.string.screen_search_title)
    val settingsTitle @Composable get() = stringResource(Res.string.screen_settings_title)

    val navBrowse @Composable get() = stringResource(Res.string.nav_browse)
    val navFavorites @Composable get() = stringResource(Res.string.nav_favorites)

    val appStartupErrorTitle @Composable get() = stringResource(Res.string.app_startup_error_title)
    val appStartupErrorSyncDesc @Composable get() = stringResource(Res.string.app_startup_error_sync_desc)
    val appStartupErrorDesc @Composable get() = stringResource(Res.string.app_startup_error_desc)
    val appErrorTryAgain @Composable get() = stringResource(Res.string.app_error_try_again)

    val searchPlaceholder @Composable get() = stringResource(Res.string.search_placeholder)
    val noSearchResult @Composable get() = stringResource(Res.string.no_search_result)
    val startSearch @Composable get() = stringResource(Res.string.start_search)
    val detailsCoatOfArms @Composable get() = stringResource(Res.string.details_coat_of_arms)
    val commonClose @Composable get() = stringResource(Res.string.common_close)
    val commonConfirm @Composable get() = stringResource(Res.string.common_confirm)
    val commonReset @Composable get() = stringResource(Res.string.common_reset)
    val commonGotIt @Composable get() = stringResource(Res.string.common_got_it)
    val commonCancel @Composable get() = stringResource(Res.string.common_cancel)

    val detailLabelLocation @Composable get() = stringResource(Res.string.detail_label_location)
    val detailLabelArea @Composable get() = stringResource(Res.string.detail_label_area)
    val detailLabelPopulation @Composable get() = stringResource(Res.string.detail_label_population)
    val detailLabelLanguages @Composable get() = stringResource(Res.string.detail_label_languages)
    val detailLabelCurrency @Composable get() = stringResource(Res.string.detail_label_currency)
    val detailLabelTimezones @Composable get() = stringResource(Res.string.detail_label_timezones)
    val detailLabelCapital @Composable get() = stringResource(Res.string.detail_label_capital)
    val detailLabelRegion @Composable get() = stringResource(Res.string.detail_label_region)

    val listItemLabelCapital @Composable get() = stringResource(Res.string.list_item_label_capital)
    @Composable fun listItemLabelCapital(capital: String) = stringResource(Res.string.list_item_label_capital_fmt, capital)

    val commonView @Composable get() = stringResource(Res.string.common_view)
    val commonRemove @Composable get() = stringResource(Res.string.common_remove)
    val commonBack @Composable get() = stringResource(Res.string.common_back)
    val commonClear @Composable get() = stringResource(Res.string.common_clear)
    val commonPrevious @Composable get() = stringResource(Res.string.common_previous)
    val commonNext @Composable get() = stringResource(Res.string.common_next)
    val commonSearch @Composable get() = stringResource(Res.string.common_search)
    val commonSettings @Composable get() = stringResource(Res.string.common_settings)
    val commonMore @Composable get() = stringResource(Res.string.common_more)
    val commonMoreOptions @Composable get() = stringResource(Res.string.common_more_options)
    val commonOpenInMaps @Composable get() = stringResource(Res.string.common_open_in_maps)
    val commonOpenInWikipedia @Composable get() = stringResource(Res.string.common_open_in_wikipedia)
    val openKeyboard @Composable get() = stringResource(Res.string.open_keyboard)
    val appLoadingMessage @Composable get() = stringResource(Res.string.app_loading_message)

    val emptyTitleNoResults @Composable get() = stringResource(Res.string.empty_title_no_results)
    val emptyMsgNoResults @Composable get() = stringResource(Res.string.empty_msg_no_results)
    val emptyTitleEmptyList @Composable get() = stringResource(Res.string.empty_title_empty_list)
    val emptyMsgEmptyList @Composable get() = stringResource(Res.string.empty_msg_empty_list)
    val emptyTitleNotFound @Composable get() = stringResource(Res.string.empty_title_not_found)
    val emptyMsgNotFound @Composable get() = stringResource(Res.string.empty_msg_not_found)

    val flashcardSettingsTitle @Composable get() = stringResource(Res.string.flashcard_settings_title)
    val flashcardVisibleClue @Composable get() = stringResource(Res.string.flashcard_visible_clue)
    val flashcardTargetPrimary @Composable get() = stringResource(Res.string.flashcard_target_primary)
    val flashcardTargetSecondary @Composable get() = stringResource(Res.string.flashcard_target_secondary)
    val flashcardTargetImage @Composable get() = stringResource(Res.string.flashcard_target_image)
    val flashcardSettingsDescription @Composable get() = stringResource(Res.string.flashcard_settings_description)
    val flashcardShuffleCards @Composable get() = stringResource(Res.string.flashcard_shuffle_cards)
    val flashcardRevealDetails @Composable get() = stringResource(Res.string.flashcard_reveal_details)
    val flashcardRestart @Composable get() = stringResource(Res.string.flashcard_restart)
    val flashcardDone @Composable get() = stringResource(Res.string.flashcard_done)
    val flashcardHintTapToReveal @Composable get() = stringResource(Res.string.flashcard_hint_tap_to_reveal)
    val flashcardMenuSettings @Composable get() = stringResource(Res.string.flashcard_menu_settings)
    val flashcardLabelCapital @Composable get() = stringResource(Res.string.flashcard_label_capital)
    val quizTypeFlashcards @Composable get() = stringResource(Res.string.quiz_type_flashcards)

    val quizTargetPrimarySecondary @Composable get() = stringResource(Res.string.quiz_target_primary_secondary)
    val quizTargetSecondaryPrimary @Composable get() = stringResource(Res.string.quiz_target_secondary_primary)
    val quizTargetImagePrimary @Composable get() = stringResource(Res.string.quiz_target_image_primary)
    val quizTargetPrimaryImage @Composable get() = stringResource(Res.string.quiz_target_primary_image)

    val choiceQuizSettingsTitle @Composable get() = stringResource(Res.string.choice_quiz_settings_title)
    val choiceQuizSelectPair @Composable get() = stringResource(Res.string.choice_quiz_select_pair)
    val choiceQuizLimitLabel @Composable get() = stringResource(Res.string.choice_quiz_limit_label)
    val choiceQuizLimitNoLimit @Composable get() = stringResource(Res.string.choice_quiz_limit_no_limit)

    val choiceQuizPromptPrimarySecondary @Composable get() = stringResource(Res.string.choice_quiz_prompt_primary_secondary)
    val choiceQuizPromptSecondaryPrimary @Composable get() = stringResource(Res.string.choice_quiz_prompt_secondary_primary)
    val choiceQuizPromptImagePrimary @Composable get() = stringResource(Res.string.choice_quiz_prompt_image_primary)
    val choiceQuizPromptPrimaryImage @Composable get() = stringResource(Res.string.choice_quiz_prompt_primary_image)

    val quizResultMetricTotal @Composable get() = stringResource(Res.string.quiz_result_metric_total)
    val quizResultMetricScore @Composable get() = stringResource(Res.string.quiz_result_metric_score)
    val quizResultMetricCorrect @Composable get() = stringResource(Res.string.quiz_result_metric_correct)
    val quizResultMetricIncorrect @Composable get() = stringResource(Res.string.quiz_result_metric_incorrect)
    val quizResultMetricSkipped @Composable get() = stringResource(Res.string.quiz_result_metric_skipped)
    val quizResultScoreDescription @Composable get() = stringResource(Res.string.quiz_result_score_description)

    val quizDescFlashcards @Composable get() = stringResource(Res.string.quiz_desc_flashcards)
    val quizDescChoicePs @Composable get() = stringResource(Res.string.quiz_desc_choice_ps)
    val quizDescChoiceIp @Composable get() = stringResource(Res.string.quiz_desc_choice_ip)
    val quizCollectionFavorites @Composable get() = stringResource(Res.string.quiz_collection_favorites)
    val quizCollectionAll @Composable get() = stringResource(Res.string.quiz_collection_all)
    val quizCollectionContinents @Composable get() = stringResource(Res.string.quiz_collection_continents)
    val quizCollectionContinentsDesc @Composable get() = stringResource(Res.string.quiz_collection_continents_desc)
    @Composable fun quizCollectionItemsCount(count: Int) = stringResource(Res.string.quiz_collection_items_count_fmt, count.toString())
    @Composable fun quizCollectionItemsCount(eligible: Int, total: Int) =
        if (eligible == total)
            stringResource(Res.string.quiz_collection_items_count_fmt, total.toString())
        else
            stringResource(Res.string.quiz_collection_items_eligible_count_fmt, eligible.toString(), total.toString())

    val quizStatUnknown @Composable get() = stringResource(Res.string.quiz_stat_unknown)
    val quizStatFamiliar @Composable get() = stringResource(Res.string.quiz_stat_familiar)
    val quizStatMastered @Composable get() = stringResource(Res.string.quiz_stat_mastered)

    val quizNoDataAlertTitle @Composable get() = stringResource(Res.string.quiz_no_data_alert_title)
    val quizNoDataAlertText @Composable get() = stringResource(Res.string.quiz_no_data_alert_text)
    val quizEligibilityInfoTitle @Composable get() = stringResource(Res.string.quiz_eligibility_info_title)
    val quizEligibilityInfoText @Composable get() = stringResource(Res.string.quiz_eligibility_info_text)

    val quizMenuResetProgress @Composable get() = stringResource(Res.string.quiz_menu_reset_progress)
    val quizResetProgressDialogTitle @Composable get() = stringResource(Res.string.quiz_reset_progress_dialog_title)
    val quizResetProgressDialogText @Composable get() = stringResource(Res.string.quiz_reset_progress_dialog_text)

    @Composable fun quizResultLastScore(score: Int, correct: Int, total: Int) = stringResource(Res.string.quiz_result_last_score_fmt, "$score%", correct.toString(), total.toString())
    @Composable fun quizResultCompletedAt(dateTime: String) = stringResource(Res.string.quiz_result_completed_at_fmt, dateTime)
    @Composable fun quizResultDateTimeAt(date: String, time: String) = stringResource(Res.string.quiz_result_date_time_at, date, time)

    @Composable
    fun detailLabelLanguage(count: Int) = if (count == 1)
        stringResource(Res.string.detail_label_language)
    else
        stringResource(Res.string.detail_label_languages)

    @Composable
    fun commonErrorsCount(count: Int) = if (count == 1)
        stringResource(Res.string.common_error_fmt, count.toString())
    else
        stringResource(Res.string.common_errors_fmt, count.toString())
}
