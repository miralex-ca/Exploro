package com.muralex.myapp.resources

import androidx.compose.runtime.Composable
import com.muralex.myapp.viewmodel.resources.FormattedText
import com.muralex.myapp.viewmodel.resources.StringRef
import com.muralex.myapp.viewmodel.resources.StringRefWithArgs


@Composable
fun StringRef.asString(): String = StringRefResolver.resolve(this)

@Composable
fun StringRefWithArgs.asStringWithArgs(firstArg: String, vararg otherArgs: String): String =
    StringRefResolver.resolve(this, firstArg, *otherArgs)

@Composable
fun StringRefWithArgs.asStringWithArg(arg: String): String =
    StringRefResolver.resolve(this, arg)

@Composable
fun FormattedText.Ref.with(firstArg: String, vararg otherArgs: String): String {
    return ref.asStringWithArgs(firstArg, *otherArgs)
}

@Composable
fun FormattedText.WithString.asString(): String {
    return ref.asStringWithArg(arg)
}

@Composable
fun FormattedText.WithRef.asString(): String {
    return ref.asStringWithArg(arg.asString())
}

@Composable
fun FormattedText.SimpleText.asString(): String {
    return textRef.asString()
}

@Composable
fun FormattedText.asString(): String {
    return when (this) {
        is FormattedText.Ref -> this.with("")
        is FormattedText.WithString -> this.asString()
        is FormattedText.WithRef -> this.asString()
        is FormattedText.SimpleText -> this.asString()
        else -> this.ref.ref.simpleName()
    }
}