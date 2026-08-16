package com.exploramus.app.resources

actual fun formatString(format: String, vararg args: Any): String {
    // Simple placeholder for iOS
    var result = format
    args.forEachIndexed { index, arg ->
        result = result.replace("%${index + 1}\$s", arg.toString())
            .replace("%s", arg.toString())
    }
    return result
}
