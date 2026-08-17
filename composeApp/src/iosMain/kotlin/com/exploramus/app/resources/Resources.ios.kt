package com.exploramus.app.resources

import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.numberWithDouble

actual fun formatString(format: String, vararg args: Any): String {
    // Simple placeholder for iOS
    var result = format
    args.forEachIndexed { index, arg ->
        result = result.replace("%${index + 1}\$s", arg.toString())
            .replace("%s", arg.toString())
    }
    return result
}

actual fun formatDecimal(
    value: Double,
    decimals: Int,
): String {
    val formatter = NSNumberFormatter().apply {
        minimumFractionDigits = 0u
        maximumFractionDigits = decimals.toULong()
    }

    return formatter.stringFromNumber(

        NSNumber.numberWithDouble(value)
    ) ?: value.toString()
}
