package com.muralex.exploramus.viewmodel.resources

/**
 * A container that holds a [StringRef] and is intended to be used with formatting arguments
 * in [FormattedText] implementations.
 *
 * @property ref The base string resource reference.
 */
data class StringRefWithArgs(
    val ref: StringRef
)

/**
 * Represents text that can be formatted with various types of arguments before being displayed in the UI.
 * This sealed interface provides a type-safe way to handle different string resource formatting
 * requirements across the application.
 */
sealed interface FormattedText {
    /** The base resource reference and its associated formatting metadata. */
    val ref: StringRefWithArgs

    /**
     * A simple resource reference that does not require any additional formatting arguments.
     * Arguments can be added on the UI side
     *
     * @property ref The base string resource reference wrapper.
     */
    data class Ref(
        override val ref: StringRefWithArgs,
    ) : FormattedText {
        companion object {
            /** Creates a [Ref] instance from a [StringRef]. */
            fun of(ref: StringRef) = Ref(StringRefWithArgs(ref))
        }
    }

    /**
     * A resource reference that requires a single [String] argument for formatting.
     *
     * @property ref The base string resource reference wrapper.
     * @property arg The string argument to be injected into the formatted resource.
     */
    data class WithString(
        override val ref: StringRefWithArgs,
        val arg: String
    ) : FormattedText {
        companion object {
            /** Creates a [WithString] instance from a [StringRef] and a [String] argument. */
            fun of(ref: StringRef, arg: String) = WithString(StringRefWithArgs(ref), arg)
        }
    }

    /**
     * A resource reference that requires another [StringRef] as a formatting argument.
     *
     * @property ref The base string resource reference wrapper.
     * @property arg The string resource reference to be resolved and injected as an argument.
     */
    data class WithRef(
        override val ref: StringRefWithArgs,
        val arg: StringRef
    ) : FormattedText {
        companion object {
            /** Creates a [WithRef] instance from two [StringRef] objects. */
            fun of(ref: StringRef, arg: StringRef) = WithRef(StringRefWithArgs(ref), arg)
        }
    }

    /**
     * Represents a string resource used directly as text, often for static labels or
     * content that doesn't follow standard multi-argument formatting patterns.
     *
     * @property ref A placeholder reference, defaulting to an empty string resource.
     * @property textRef The actual string resource reference to be displayed.
     */
    data class SimpleText(
        override val ref: StringRefWithArgs = StringRefWithArgs(SharedRes.Strings.empty_ref_placeholder),
        val textRef: StringRef
    ) : FormattedText {
        companion object {
            /** Creates a [SimpleText] instance from a [StringRef]. */
            fun of(arg: StringRef) = SimpleText(textRef = arg)
        }
    }
}
