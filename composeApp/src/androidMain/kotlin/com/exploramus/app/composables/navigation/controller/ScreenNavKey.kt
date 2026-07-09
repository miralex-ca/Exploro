package com.exploramus.app.composables.navigation.controller

import androidx.navigation3.runtime.NavKey
import com.exploramus.shared.viewmodel.core.ScreenIdentifier

/**
 * Nav3's NavKey adapter for ScreenIdentifier.
 * ScreenIdentifier isn't a data class, so equality/hashing is
 * explicitly keyed off URI to match how identity is used everywhere else
 */
data class ScreenNavKey(val screenIdentifier: ScreenIdentifier) : NavKey {
    override fun equals(other: Any?): Boolean =
        other is ScreenNavKey && other.screenIdentifier.URI == screenIdentifier.URI

    override fun hashCode(): Int = screenIdentifier.URI.hashCode()
}