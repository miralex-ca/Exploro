package com.muralex.exploramus.viewmodel


import com.muralex.data.repository.Repository
import com.muralex.exploramus.DebugLogger

val debugLogger by lazy { DebugLogger("D-KMP SAMPLE") }


class DKMPViewModel (repo: Repository) {

    companion object Factory {
        // factory methods are defined in the platform-specific shared code (androidMain and iosMain)
    }
    private val stateManager by lazy { StateManager(repo) }
    val navigation by lazy { Navigation(stateManager) }

}