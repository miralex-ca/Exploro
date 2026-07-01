package com.exploramus.data.localdb

import appLocalDb.AppLocalDb
import com.exploramus.core.models.Section

fun AppLocalDb.setSections(sections: List<Section>) {
    sectionsQueries.transaction {
        sectionsQueries.deleteAllSections()
        sections.forEachIndexed { index, section ->
            sectionsQueries.insertSection(
                id = section.id,
                name = section.name,
                order_index = index.toLong()
            )
        }
    }
}

fun AppLocalDb.getSections(): List<Section> {
    return sectionsQueries.getAllSections { id, name, _ ->
        Section(id = id, name = name)
    }.executeAsList()
}
