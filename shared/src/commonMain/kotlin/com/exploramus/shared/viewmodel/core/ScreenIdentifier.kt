package com.exploramus.shared.viewmodel.core

import com.exploramus.shared.viewmodel.screens.Level1Navigation
import com.exploramus.shared.viewmodel.screens.Screen
import kotlinx.serialization.Serializable

typealias URI = String

@Serializable
data class ScreenIdentifier(
    val screen : Screen,
    val params: ScreenParams? = null,
) {

    val URI : URI
        get() = returnURI()

    companion object Factory {
        fun get(screen: Screen, params: ScreenParams? = null): ScreenIdentifier {
            return ScreenIdentifier(screen, params)
        }

        /**
         * Parses a URI string into a ScreenIdentifier.
         * Note: This is primarily used for deep linking or simple persistence.
         * For complex parameters, full polymorphic serialization is preferred.
         */
        fun getByURI(URI: String): ScreenIdentifier? {
            val parts = URI.split(":")
            val screenName = parts[0]
            
            val screen = Screen.entries.find { it.asString == screenName }
            if (screen != null) {
                // If there's an old-format ":nul" or similar, we just treat it as no params
                return ScreenIdentifier(screen, null)
            }
            return null
        }
    }

    private fun returnURI() : String {
        if (params == null) return screen.asString
        val toString = params.toString() // returns `ClassParams(A=1&B=2)`
        val startIndex = toString.indexOf("(")
        if (startIndex == -1) return screen.asString + ":" + toString
        val paramsString = toString.substring(startIndex + 1, toString.length - 1)
        return screen.asString  + ":" + paramsString
    }

    // unlike the "params" property, this reified function returns the specific type and not the generic "ScreenParams" interface type
    inline fun <reified T: ScreenParams> screenParams() : T {
        return params as T
    }

    fun getScreenInitSettings(stateManager: StateManager) : ScreenInitSettings {
        return screen.initSettings(stateManager,this)
    }

    fun level1VerticalBackstackEnabled() : Boolean {
        Level1Navigation.entries.forEach {
            if (it.screenIdentifier.URI == this.URI && it.rememberVerticalStack) {
                return true
            }
        }
        return false
    }

}
