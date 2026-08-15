package com.exploramus.app.resources

import androidx.compose.runtime.Composable

@Composable
fun stringRes(
    id: String,
    vararg formatArgs: Any
): String {
    return stringResource(id, *formatArgs)
}

object Strings {
    val homeTitle @Composable get() = stringRes("screen_home_title")
    val favoritesTitle @Composable get() = stringRes("screen_favorites_title")
    val searchTitle @Composable get() = stringRes("screen_search_title")
    val settingsTitle @Composable get() = stringRes("screen_settings_title")

    val navBrowse @Composable get() = stringRes("nav_browse")
    val navFavorites @Composable get() = stringRes("nav_favorites")

    val appStartupErrorTitle @Composable get() = stringRes("app_startup_error_title")
    val appStartupErrorSyncDesc @Composable get() = stringRes("app_startup_error_sync_desc")
    val appStartupErrorDesc @Composable get() = stringRes("app_startup_error_desc")
    val appErrorTryAgain @Composable get() = stringRes("app_error_try_again")

    val searchPlaceholder @Composable get() = stringRes("search_placeholder")
    val noSearchResult @Composable get() = stringRes("no_search_result")
    val startSearch @Composable get() = stringRes("start_search")
    val detailsCoatOfArms @Composable get() = stringRes("details_coat_of_arms")
    val commonClose @Composable get() = stringRes("common_close")
    val commonConfirm @Composable get() = stringRes("common_confirm")
    val commonReset @Composable get() = stringRes("common_reset")
    val commonGotIt @Composable get() = stringRes("common_got_it")
    val commonCancel @Composable get() = stringRes("common_cancel")

    val detailLabelLocation @Composable get() = stringRes("detail_label_location")
    val detailLabelArea @Composable get() = stringRes("detail_label_area")
    val detailLabelPopulation @Composable get() = stringRes("detail_label_population")
    val detailLabelLanguages @Composable get() = stringRes("detail_label_languages")
    val detailLabelCurrency @Composable get() = stringRes("detail_label_currency")
    val detailLabelTimezones @Composable get() = stringRes("detail_label_timezones")
    val detailLabelCapital @Composable get() = stringRes("detail_label_capital")
    val detailLabelRegion @Composable get() = stringRes("detail_label_region")

    val listItemLabelCapital @Composable get() = stringRes("list_item_label_capital")
    @Composable fun listItemLabelCapital(capital: String) = stringRes("list_item_label_capital_fmt", capital)

    val commonView @Composable get() = stringRes("common_view")
    val commonRemove @Composable get() = stringRes("common_remove")
    val commonBack @Composable get() = stringRes("common_back")
    val commonClear @Composable get() = stringRes("common_clear")
    val commonPrevious @Composable get() = stringRes("common_previous")
    val commonNext @Composable get() = stringRes("common_next")
    val commonSearch @Composable get() = stringRes("common_search")
    val commonSettings @Composable get() = stringRes("common_settings")
    val commonMore @Composable get() = stringRes("common_more")
    val commonMoreOptions @Composable get() = stringRes("common_more_options")
    val commonOpenInMaps @Composable get() = stringRes("common_open_in_maps")
    val commonOpenInWikipedia @Composable get() = stringRes("common_open_in_wikipedia")
    val openKeyboard @Composable get() = stringRes("open_keyboard")
    val appLoadingMessage @Composable get() = stringRes("app_loading_message")

    val emptyTitleNoResults @Composable get() = stringRes("empty_title_no_results")
    val emptyMsgNoResults @Composable get() = stringRes("empty_msg_no_results")
    val emptyTitleEmptyList @Composable get() = stringRes("empty_title_empty_list")
    val emptyMsgEmptyList @Composable get() = stringRes("empty_msg_empty_list")
    val emptyTitleNotFound @Composable get() = stringRes("empty_title_not_found")
    val emptyMsgNotFound @Composable get() = stringRes("empty_msg_not_found")

    val flashcardSettingsTitle @Composable get() = stringRes("flashcard_settings_title")
    val flashcardVisibleClue @Composable get() = stringRes("flashcard_visible_clue")
    val flashcardTargetPrimary @Composable get() = stringRes("flashcard_target_primary")
    val flashcardTargetSecondary @Composable get() = stringRes("flashcard_target_secondary")
    val flashcardTargetImage @Composable get() = stringRes("flashcard_target_image")
    val flashcardSettingsDescription @Composable get() = stringRes("flashcard_settings_description")
    val flashcardShuffleCards @Composable get() = stringRes("flashcard_shuffle_cards")
    val flashcardRevealDetails @Composable get() = stringRes("flashcard_reveal_details")
    val flashcardRestart @Composable get() = stringRes("flashcard_restart")
    val flashcardDone @Composable get() = stringRes("flashcard_done")
    val flashcardHintTapToReveal @Composable get() = stringRes("flashcard_hint_tap_to_reveal")
    val flashcardMenuSettings @Composable get() = stringRes("flashcard_menu_settings")
    val flashcardLabelCapital @Composable get() = stringRes("flashcard_label_capital")
    val quizTypeFlashcards @Composable get() = stringRes("quiz_type_flashcards")

    val quizTargetPrimarySecondary @Composable get() = stringRes("quiz_target_primary_secondary")
    val quizTargetSecondaryPrimary @Composable get() = stringRes("quiz_target_secondary_primary")
    val quizTargetImagePrimary @Composable get() = stringRes("quiz_target_image_primary")
    val quizTargetPrimaryImage @Composable get() = stringRes("quiz_target_primary_image")

    val choiceQuizSettingsTitle @Composable get() = stringRes("choice_quiz_settings_title")
    val choiceQuizSelectPair @Composable get() = stringRes("choice_quiz_select_pair")
    val choiceQuizLimitLabel @Composable get() = stringRes("choice_quiz_limit_label")
    val choiceQuizLimitNoLimit @Composable get() = stringRes("choice_quiz_limit_no_limit")

    val choiceQuizPromptPrimarySecondary @Composable get() = stringRes("choice_quiz_prompt_primary_secondary")
    val choiceQuizPromptSecondaryPrimary @Composable get() = stringRes("choice_quiz_prompt_secondary_primary")
    val choiceQuizPromptImagePrimary @Composable get() = stringRes("choice_quiz_prompt_image_primary")
    val choiceQuizPromptPrimaryImage @Composable get() = stringRes("choice_quiz_prompt_primary_image")

    val quizResultMetricTotal @Composable get() = stringRes("quiz_result_metric_total")
    val quizResultMetricScore @Composable get() = stringRes("quiz_result_metric_score")
    val quizResultMetricCorrect @Composable get() = stringRes("quiz_result_metric_correct")
    val quizResultMetricIncorrect @Composable get() = stringRes("quiz_result_metric_incorrect")
    val quizResultMetricSkipped @Composable get() = stringRes("quiz_result_metric_skipped")
    val quizResultScoreDescription @Composable get() = stringRes("quiz_result_score_description")

    val quizDescFlashcards @Composable get() = stringRes("quiz_desc_flashcards")
    val quizDescChoicePs @Composable get() = stringRes("quiz_desc_choice_ps")
    val quizDescChoiceIp @Composable get() = stringRes("quiz_desc_choice_ip")
    val quizCollectionFavorites @Composable get() = stringRes("quiz_collection_favorites")
    val quizCollectionAll @Composable get() = stringRes("quiz_collection_all")
    val quizCollectionContinents @Composable get() = stringRes("quiz_collection_continents")
    val quizCollectionContinentsDesc @Composable get() = stringRes("quiz_collection_continents_desc")
    @Composable fun quizCollectionItemsCount(count: Int) = stringRes("quiz_collection_items_count_fmt", count)
    @Composable fun quizCollectionItemsCount(eligible: Int, total: Int) =
        if (eligible == total)
            stringRes("quiz_collection_items_count_fmt", total)
        else
            stringRes("quiz_collection_items_eligible_count_fmt", eligible, total)

    val quizStatUnknown @Composable get() = stringRes("quiz_stat_unknown")
    val quizStatFamiliar @Composable get() = stringRes("quiz_stat_familiar")
    val quizStatMastered @Composable get() = stringRes("quiz_stat_mastered")

    val quizNoDataAlertTitle @Composable get() = stringRes("quiz_no_data_alert_title")
    val quizNoDataAlertText @Composable get() = stringRes("quiz_no_data_alert_text")
    val quizEligibilityInfoTitle @Composable get() = stringRes("quiz_eligibility_info_title")
    val quizEligibilityInfoText @Composable get() = stringRes("quiz_eligibility_info_text")

    val quizMenuResetProgress @Composable get() = stringRes("quiz_menu_reset_progress")
    val quizResetProgressDialogTitle @Composable get() = stringRes("quiz_reset_progress_dialog_title")
    val quizResetProgressDialogText @Composable get() = stringRes("quiz_reset_progress_dialog_text")

    @Composable fun quizResultLastScore(score: Int, correct: Int, total: Int) = stringRes("quiz_result_last_score_fmt", score, correct, total)
    @Composable fun quizResultCompletedAt(dateTime: String) = stringRes("quiz_result_completed_at_fmt", dateTime)
    @Composable fun quizResultDateTimeAt(date: String, time: String) = stringRes("quiz_result_date_time_at", date, time)

    @Composable
    fun detailLabelLanguage(count: Int) = if (count == 1)
        stringRes("detail_label_language")
    else
        stringRes("detail_label_languages")

    @Composable
    fun commonErrorsCount(count: Int) = if (count == 1)
        stringRes("common_error_fmt", count)
    else
        stringRes("common_errors_fmt", count)
}
