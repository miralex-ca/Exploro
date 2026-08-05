package com.exploramus.shared.viewmodel.screens.quizzes.common

import com.exploramus.core.models.Country
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

interface DistractorPoolProvider {
    suspend fun preload(targets: List<Country>)
    fun poolFor(target: Country): List<Country>
}

class SameListDistractorPoolProvider(
    private val pool: List<Country>
) : DistractorPoolProvider {
    override suspend fun preload(targets: List<Country>) = Unit
    override fun poolFor(target: Country): List<Country> = pool
}

class SectionDistractorPoolProvider(
    private val dataSource: ChoiceQuizDataSource,
    private val minPoolSize: Int = 4
) : DistractorPoolProvider {

    private val cache = mutableMapOf<String, List<Country>>()
    private var allCountriesFallback: List<Country> = emptyList()

    private fun normalize(name: String) = name.trim().lowercase()

    override suspend fun preload(targets: List<Country>) {
        val continentNames = targets.map { it.continent }.distinct()

        val sections = dataSource.getSections()
        val sectionIdByNormalizedName = sections.associateBy({ normalize(it.name) }, { it.id })

        coroutineScope {
            continentNames
                .mapNotNull { name ->
                    val sectionId = sectionIdByNormalizedName[normalize(name)] ?: return@mapNotNull null
                    async { name to dataSource.getItemsBySection(sectionId) }
                }
                .awaitAll()
        }.forEach { (name, countries) -> cache[normalize(name)] = countries }

        val needsFallback = cache.values.any { it.size < minPoolSize } ||
                continentNames.any { normalize(it) !in cache }
        if (needsFallback) {
            allCountriesFallback = dataSource.getItemsFromAll()
        }
    }

    override fun poolFor(target: Country): List<Country> {
        val sectionPool = cache[normalize(target.continent)].orEmpty()
        return if (sectionPool.size >= minPoolSize) sectionPool else allCountriesFallback
    }
}