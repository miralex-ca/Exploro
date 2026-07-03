package com.exploramus.app.composables.screens.section

import com.exploramus.app.composables.navigation.controller.DetailsNavParams
import com.exploramus.app.composables.navigation.controller.DetailsPagerNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.section.SectionListItem

sealed class SectionUiEvent {
    data class OnItemClicked(val item: SectionListItem, val sectionId: String) : SectionUiEvent()
}

class SectionEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: SectionUiEvent) {
        when (event) {
            is SectionUiEvent.OnItemClicked -> {
                navActions.toDetailsPagerFromList(event.item.toDetailsPagerNavParams(event.sectionId))
            }
        }
    }
}

fun SectionListItem.toDetailsNavParams() = DetailsNavParams(id, name)
fun SectionListItem.toDetailsPagerNavParams(sectionId: String) = DetailsPagerNavParams(id, sectionId, name)

