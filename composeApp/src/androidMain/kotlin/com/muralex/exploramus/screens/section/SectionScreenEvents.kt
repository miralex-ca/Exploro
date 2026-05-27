package com.muralex.exploramus.screens.section

import com.muralex.exploramus.navigation.DetailsNavParams
import com.muralex.exploramus.navigation.ScreenNavActions
import com.muralex.exploramus.viewmodel.screens.section.SectionListItem

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

