package com.exploramus.app.resources

import java.util.Locale

actual fun formatString(format: String, vararg args: Any): String {
    return String.format(format, *args)
}

actual fun formatDecimal(
    value: Double,
    decimals: Int,
): String {
    return String.format(
        Locale.US,
        "%.${decimals}f",
        value,
    )
}