package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.exploramus.app.R

@Composable
fun stringRes(
    resId: Int,
    vararg formatArgs: Any
): String {
    return stringResource(resId, *formatArgs)
}

object Strings {
    val homeTitle @Composable get() = stringRes(R.string.screen_home_title)
    val favoritesTitle @Composable get() = stringRes(R.string.screen_favorites_title)
    val searchTitle @Composable get() = stringRes(R.string.screen_search_title)
    val settingsTitle @Composable get() = stringRes(R.string.screen_settings_title)

    val navBrowse @Composable get() = stringRes(R.string.nav_browse)
    val navFavorites @Composable get() = stringRes(R.string.nav_favorites)

    val appStartupErrorTitle @Composable get() = stringRes(R.string.app_startup_error_title)
    val appStartupErrorSyncDesc @Composable get() = stringRes(R.string.app_startup_error_sync_desc)
    val appStartupErrorDesc @Composable get() = stringRes(R.string.app_startup_error_desc)
    val appErrorTryAgain @Composable get() = stringRes(R.string.app_error_try_again)

    val searchPlaceholder @Composable get() = stringRes(R.string.search_placeholder)
    val noSearchResult @Composable get() = stringRes(R.string.no_search_result)
    val startSearch @Composable get() = stringRes(R.string.start_search)
    val detailsCoatOfArms @Composable get() = stringRes(R.string.details_coat_of_arms)
    val commonClose @Composable get() = stringRes(R.string.common_close)
    val commonConfirm @Composable get() = stringRes(R.string.common_confirm)
    val commonCancel @Composable get() = stringRes(R.string.common_cancel)

    val detailLabelLocation @Composable get() = stringRes(R.string.detail_label_location)
    val detailLabelArea @Composable get() = stringRes(R.string.detail_label_area)
    val detailLabelPopulation @Composable get() = stringRes(R.string.detail_label_population)
    val detailLabelLanguages @Composable get() = stringRes(R.string.detail_label_languages)
    val detailLabelCurrency @Composable get() = stringRes(R.string.detail_label_currency)
    val detailLabelTimezones @Composable get() = stringRes(R.string.detail_label_timezones)
    val detailLabelCapital @Composable get() = stringRes(R.string.detail_label_capital)
    val detailLabelRegion @Composable get() = stringRes(R.string.detail_label_region)

    val listItemLabelCapital @Composable get() = stringRes(R.string.list_item_label_capital)
    @Composable fun listItemLabelCapital(capital: String) = stringRes(R.string.list_item_label_capital_fmt, capital)

    val commonView @Composable get() = stringRes(R.string.common_view)
    val commonRemove @Composable get() = stringRes(R.string.common_remove)
    val commonBack @Composable get() = stringRes(R.string.common_back)
    val commonClear @Composable get() = stringRes(R.string.common_clear)
    val commonPrevious @Composable get() = stringRes(R.string.common_previous)
    val commonNext @Composable get() = stringRes(R.string.common_next)
    val commonSearch @Composable get() = stringRes(R.string.common_search)
    val commonSettings @Composable get() = stringRes(R.string.common_settings)
    val commonMore @Composable get() = stringRes(R.string.common_more)
    val commonMoreOptions @Composable get() = stringRes(R.string.common_more_options)
    val commonOpenInMaps @Composable get() = stringRes(R.string.common_open_in_maps)
    val commonOpenInWikipedia @Composable get() = stringRes(R.string.common_open_in_wikipedia)
    val openKeyboard @Composable get() = stringRes(R.string.open_keyboard)
    val appLoadingMessage @Composable get() = stringRes(R.string.app_loading_message)

    val emptyTitleNoResults @Composable get() = stringRes(R.string.empty_title_no_results)
    val emptyMsgNoResults @Composable get() = stringRes(R.string.empty_msg_no_results)
    val emptyTitleEmptyList @Composable get() = stringRes(R.string.empty_title_empty_list)
    val emptyMsgEmptyList @Composable get() = stringRes(R.string.empty_msg_empty_list)
    val emptyTitleNotFound @Composable get() = stringRes(R.string.empty_title_not_found)
    val emptyMsgNotFound @Composable get() = stringRes(R.string.empty_msg_not_found)

    val flashcardSettingsTitle @Composable get() = stringRes(R.string.flashcard_settings_title)
    val flashcardVisibleClue @Composable get() = stringRes(R.string.flashcard_visible_clue)
    val flashcardTargetPrimary @Composable get() = stringRes(R.string.flashcard_target_primary)
    val flashcardTargetSecondary @Composable get() = stringRes(R.string.flashcard_target_secondary)
    val flashcardTargetImage @Composable get() = stringRes(R.string.flashcard_target_image)
    val flashcardSettingsDescription @Composable get() = stringRes(R.string.flashcard_settings_description)
    val flashcardShuffleCards @Composable get() = stringRes(R.string.flashcard_shuffle_cards)
    val flashcardRevealDetails @Composable get() = stringRes(R.string.flashcard_reveal_details)
    val flashcardRestart @Composable get() = stringRes(R.string.flashcard_restart)
    val flashcardDone @Composable get() = stringRes(R.string.flashcard_done)
    val flashcardHintTapToReveal @Composable get() = stringRes(R.string.flashcard_hint_tap_to_reveal)
    val flashcardMenuSettings @Composable get() = stringRes(R.string.flashcard_menu_settings)
    val flashcardLabelCapital @Composable get() = stringRes(R.string.flashcard_label_capital)

    val quizTargetPrimarySecondary @Composable get() = stringRes(R.string.quiz_target_primary_secondary)
    val quizTargetSecondaryPrimary @Composable get() = stringRes(R.string.quiz_target_secondary_primary)
    val quizTargetImagePrimary @Composable get() = stringRes(R.string.quiz_target_image_primary)
    val quizTargetPrimaryImage @Composable get() = stringRes(R.string.quiz_target_primary_image)

    val choiceQuizSettingsTitle @Composable get() = stringRes(R.string.choice_quiz_settings_title)
    val choiceQuizSelectPair @Composable get() = stringRes(R.string.choice_quiz_select_pair)
    val choiceQuizLimitLabel @Composable get() = stringRes(R.string.choice_quiz_limit_label)
    val choiceQuizLimitNoLimit @Composable get() = stringRes(R.string.choice_quiz_limit_no_limit)

    @Composable
    fun detailLabelLanguage(count: Int) = if (count == 1)
        stringRes(R.string.detail_label_language)
    else
        stringRes(R.string.detail_label_languages)
}








