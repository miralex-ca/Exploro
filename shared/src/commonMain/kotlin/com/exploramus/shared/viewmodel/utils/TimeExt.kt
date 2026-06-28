package com.exploramus.shared.viewmodel.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toFormattedDate(): String? {
    return runCatching {
        val instant = Instant.fromEpochSeconds(this)
        val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
        val day = dateTime.dayOfMonth.toString().padStart(2, '0')
        val month = dateTime.monthNumber.toString().padStart(2, '0')
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