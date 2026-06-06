package com.muralex.exploramus.screens.home

import com.muralex.exploramus.navigation.controller.DetailsNavParams
import com.muralex.exploramus.navigation.controller.ScreenNavActions
import com.muralex.exploramus.navigation.controller.SectionNavParams
import com.muralex.exploramus.viewmodel.screens.home.HomeListItem
import com.muralex.exploramus.viewmodel.screens.home.HomeSectionState

sealed class HomeUiEvent {
    data class OnItemClicked(val item: HomeListItem) : HomeUiEvent()
    data class OnSectionClicked(val section: HomeSectionState) : HomeUiEvent()
}

class HomeEventHandler(
    val navActions: ScreenNavActions
) {
    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OnItemClicked ->
                navActions.toDetailFromList(event.item.toDetailsNavParams())

            is HomeUiEvent.OnSectionClicked ->
                navActions.toSection(event.section.toSectionNavParams())
        }
    }
}

fun HomeListItem.toDetailsNavParams() = DetailsNavParams(id, name)
fun HomeSectionState.toSectionNavParams() = SectionNavParams(sectionId, sectionName)

