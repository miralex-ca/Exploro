package com.exploramus.app.resources

import androidx.compose.runtime.Composable
import com.exploramus.shared.resources.FormattedText
import com.exploramus.shared.resources.StringRef
import com.exploramus.shared.resources.StringRefWithArgs

@Composable
fun StringRef.asString(): String = StringRefResolver.resolve(this)

@Composable
fun StringRefWithArgs.asStringWithArgs(firstArg: String, vararg otherArgs: String): String =
    StringRefResolver.resolve(this, firstArg, *otherArgs)

@Composable
fun StringRefWithArgs.asStringWithArg(arg: String): String =
    StringRefResolver.resolve(this, arg)

@Composable
fun FormattedText.asString(): String {
    return when (this) {
        is FormattedText.Ref -> this.ref.ref.asString()
        is FormattedText.WithString -> this.ref.asStringWithArg(this.arg)
        is FormattedText.WithRef -> this.ref.asStringWithArg(this.arg.asString())
        is FormattedText.SimpleText -> this.textRef.asString()
    }
}

@Composable
fun FormattedText.Ref.with(firstArg: String, vararg otherArgs: String): String {
    return ref.asStringWithArgs(firstArg, *otherArgs)
}
