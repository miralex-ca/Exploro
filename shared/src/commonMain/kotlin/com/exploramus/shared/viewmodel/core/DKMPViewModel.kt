package com.exploramus.shared.viewmodel.core

import com.exploramus.data.repository.Repository


class DKMPViewModel (repo: Repository) {

    companion object Factory {
        // factory methods are defined in the platform-specific shared code (androidMain and iosMain)
    }
    private val stateManager by lazy { StateManager(repo) }
    val navigation by lazy { Navigation(stateManager) }

}