package com.exploramus.shared.viewmodel.core

import com.exploramus.core.common.logging.Log
import kotlinx.coroutines.launch

class Events (val stateManager : StateManager) {

    val dataRepository
        get() = stateManager.dataRepository

    // we run each event function on a Dispatchers.Main coroutine
    fun screenCoroutine (block: suspend () -> Unit) {
        Log.d("/"+stateManager.currentScreenIdentifier.URI+": an Event is called")
        stateManager.runInScreenScope { block() }
    }

    fun appCoroutine(block: suspend () -> Unit) {
        stateManager.appScope.launch { block() }
    }

}