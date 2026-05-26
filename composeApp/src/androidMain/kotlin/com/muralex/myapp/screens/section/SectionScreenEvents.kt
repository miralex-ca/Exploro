package com.muralex.myapp.screens.section

import com.muralex.myapp.navigation.DetailsNavParams
import com.muralex.myapp.navigation.ScreenNavActions
import com.muralex.myapp.viewmodel.screens.section.SectionListItem

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

