package com.exploramus.app.composables.screens.section

import com.exploramus.app.composables.navigation.controller.DetailsNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.section.SectionListItem

sealed class SectionUiEvent {
    data class OnItemClicked(val item: SectionListItem) : SectionUiEvent()
}

class SectionEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: SectionUiEvent) {
        when (event) {
            is SectionUiEvent.OnItemClicked -> {
                navActions.toDetailFromList(event.item.toDetailsNavParams())
            }
        }
    }
}

fun SectionListItem.toDetailsNavParams() = DetailsNavParams(id, name)

