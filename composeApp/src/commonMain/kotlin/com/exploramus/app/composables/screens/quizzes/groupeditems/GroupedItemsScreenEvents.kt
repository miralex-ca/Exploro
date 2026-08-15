package com.exploramus.app.composables.screens.quizzes.groupeditems

import com.exploramus.app.composables.navigation.controller.DetailsNavParams
import com.exploramus.app.composables.navigation.controller.ScreenNavActions
import com.exploramus.shared.viewmodel.screens.quizzes.groupeditems.MasteryItemState

sealed interface GroupedItemsUiEvent {
    data class OnItemClicked(val item: MasteryItemState) : GroupedItemsUiEvent
}

class GroupedItemsEventHandler(
    private val navActions: ScreenNavActions
) {
    fun onEvent(event: GroupedItemsUiEvent) {
        when (event) {
            is GroupedItemsUiEvent.OnItemClicked -> {
                navActions.toDetailFromList(event.item.toDetailsNavParams())
            }
        }
    }
}

fun MasteryItemState.toDetailsNavParams() = DetailsNavParams(id, name)
