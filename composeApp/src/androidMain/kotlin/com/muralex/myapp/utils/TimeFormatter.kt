package com.muralex.myapp.utils

//@OptIn(ExperimentalTime::class)
//fun Long.toFormattedDateTime(): String {
//    val instant = Instant.fromEpochSeconds(this)
//
//    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
//
//    return "${dateTime.dayOfMonth.toString().padStart(2, '0')}/" +
//            "${dateTime.monthNumber.toString().padStart(2, '0')}/" +
//            "${dateTime.year.toString().takeLast(2)} " +
//            "${dateTime.hour.toString().padStart(2, '0')}:" +
//            "${dateTime.minute.toString().padStart(2, '0')}"
//}