package com.exploramus.data.localdb

import app.cash.sqldelight.db.SqlDriver
import appLocalDb.AppLocalDb
import com.exploramus.core.models.Country
import com.exploramus.core.models.CountryDetails
import com.exploramus.core.models.CountryWithDetails
import com.exploramus.core.models.QuizResult
import com.exploramus.core.models.Section
import com.exploramus.data.common.LocalDataSource

internal object DatabaseConfig {
    const val NAME = "applocal.db"
    const val VERSION = 10L
}

internal fun createLocalDataSource(sqlDriver: SqlDriver): LocalDataSource {
    val appLocalDb = AppLocalDb(sqlDriver)
    return SQLDelightLocalDataSource(
        appLocalDb,
        DatabaseManager.Base(sqlDriver, appLocalDb),
    )
}

internal class SQLDelightLocalDataSource(
    private val database:  AppLocalDb,
    private val dbManager: DatabaseManager,
) : LocalDataSource {

    override val databaseVersion: Long
        get() = DatabaseConfig.VERSION

    private val favoritesQueries = database.favoritesQueries

    override suspend fun setCountriesList(list: List<Country>) {
        database.setCountriesList(list)
    }

    override suspend fun setCountryDetailsList(list: List<CountryDetails>) {
        database.setCountriesDetailsList(list)
    }

    override suspend fun getAllCountries(): List<Country> {
        return database.getAllCountries().toCountryList()
    }

    override suspend fun getAllCountriesBySectionId(sectionId: String): List<Country> {
        val sectionName = database.sectionsQueries.getSectionNameById(sectionId).executeAsOneOrNull() ?: sectionId
        return database.getAllCountriesBySection(sectionName).toCountryList()
    }

    override suspend fun getAllCountriesWithDetailsBySectionId(sectionId: String): List<CountryWithDetails> {
        val sectionName = database.sectionsQueries.getSectionNameById(sectionId).executeAsOneOrNull() ?: sectionId
        return database.getAllCountriesWithDetailsBySection(sectionName).map { it.toCountryWithDetails() }
    }

    override suspend fun searchCountries(query: String): List<Country> {
        return database.searchCountries(query).toCountryList()
    }

    override suspend fun getAllCountriesWithDetails(): List<CountryWithDetails> {
        return database.getAllCountriesWithDetails().map { it.toCountryWithDetails() }
    }

    override suspend fun hasCountriesData(): Boolean {
        return database.getCountriesCount() > 0
    }

    override suspend fun getCountriesBySection(sectionId: String, limit: Long) : List<Country> {
        val sectionName = database.sectionsQueries.getSectionNameById(sectionId).executeAsOneOrNull() ?: sectionId
        return database.getCountriesByContinent(sectionName, limit).toCountryList()
    }

    override suspend fun isFavorite(id: String): Boolean {
        return favoritesQueries
            .isFavorite(id)
            .executeAsOne()
    }

    override suspend fun addFavorite(id: String) {
        favoritesQueries.addFavorite(id)
    }

    override suspend fun removeFavorite(id: String) {
        favoritesQueries.removeFavorite(id)
    }

    override suspend fun getFavorites(): List<Country> {
        return database.getFavorites().toCountryList()
    }

    override suspend fun getFavoritesWithDetails(): List<CountryWithDetails> {
        return database.getFavoritesWithDetails().map { it.toCountryWithDetails() }
    }

    override suspend fun resetAndMigrate() {
        dbManager.rebuildDatabase()
    }

    override suspend fun getCountryDetailsById(id: String): CountryWithDetails? {
        return database.getCountryDetailsById(id)?.toCountryWithDetails()
    }

    override suspend fun setSections(sections: List<Section>) {
        database.setSections(sections)
    }

    override suspend fun getSections(): List<Section> {
        return database.getSections()
    }

    override suspend fun getFavoritesCount(): Long {
        return database.getFavoritesCount()
    }

    override suspend fun getAllCountriesCount(): Long {
        return database.getCountriesCount()
    }

    override suspend fun getCountriesCountBySection(sectionId: String): Long {
        val sectionName = database.sectionsQueries.getSectionNameById(sectionId).executeAsOneOrNull() ?: sectionId
        return database.getCountriesCountByContinent(sectionName)
    }

    override suspend fun saveQuizResult(
        quizId: String,
        correctAnswers: Int,
        totalAnswers: Int,
        completedAt: Long
    ) {
        database.quizResultsQueries.insertQuizResult(
            quiz_id = quizId,
            completed_at = completedAt,
            correct_answers = correctAnswers.toLong(),
            total_answers = totalAnswers.toLong()
        )
    }

    override suspend fun getQuizResult(quizId: String): QuizResult? {
        return database.quizResultsQueries
            .getQuizResult(quizId)
            .executeAsOneOrNull()
            ?.toQuizResult()
    }

    override suspend fun getQuizResults(quizIds: List<String>): List<QuizResult> {
        return database.quizResultsQueries
            .getQuizResultsByIds(quizIds)
            .executeAsList()
            .toQuizResultList()
    }
}
