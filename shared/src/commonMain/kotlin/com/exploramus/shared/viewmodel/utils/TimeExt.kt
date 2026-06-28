package com.exploramus.shared.viewmodel.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun Long.toFormattedDate(): String? {
    return runCatching {
        val instant = Instant.fromEpochSeconds(this)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val day = dateTime.day.toString().padStart(2, '0')
        val month = dateTime.month.number.toString().padStart(2, '0')
        val year = dateTime.year.toString()
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')
        "$hour:$minute · $day.$month.$year"
    }.fold(
        onSuccess = {
            it
        },
        onFailure = { _ ->
            null
        }
    )
}