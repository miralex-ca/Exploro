package com.exploramus.app.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import androidx.compose.ui.platform.LocalContext
import com.exploramus.data.repository.functions.exportDataToJson
import com.exploramus.shared.viewmodel.core.Navigation
import com.exploramus.shared.viewmodel.utils.SingleEffect
import java.io.File

@Composable
fun SingleEffect(effect: SingleEffect, consume: () -> Unit, perform: suspend () -> Unit) {
    LaunchedEffect(effect.id) {
        if (effect.pending) {
            perform()
            consume()
            println("SingleEffect: consumed")
        }
    }
}

@Composable
fun <T> OnChange(value: T, action: suspend (old: T, new: T) -> Unit) {
    val previous = retain { mutableStateOf(value) }
    LaunchedEffect(value) {
        if (previous.value != value) {
            val old = previous.value
            previous.value = value
            action(old, value)
        }
    }
}

@Composable
fun OnAppear(action: suspend () -> Unit) {
    val appeared = retain { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!appeared.value) {
            appeared.value = true
            action()
        }
    }
}

@Composable
fun Navigation.ExportDataFromDb() {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val (countriesJson, detailsJson) = events.dataRepository.exportDataToJson()

        val countriesFile = File(context.getExternalFilesDir(null), "countries_data.json")
        countriesFile.writeText(countriesJson)
        println("Countries export done: ${countriesFile.absolutePath}")

        val detailsFile = File(context.getExternalFilesDir(null), "countries_detail_data.json")
        detailsFile.writeText(detailsJson)
        println("Details export done: ${detailsFile.absolutePath}")
    }
}