package com.muralex.exploramus.viewmodel

import kotlinx.coroutines.launch

class Events (val stateManager : StateManager, val navigation: Navigation) {

    val dataRepository
        get() = stateManager.dataRepository

    // we run each event function on a Dispatchers.Main coroutine
    fun screenCoroutine (block: suspend () -> Unit) {
        debugLogger.log("/"+stateManager.currentScreenIdentifier.URI+": an Event is called")
        stateManager.runInScreenScope { block() }
    }

    fun appCoroutine(block: suspend () -> Unit) {
        stateManager.appScope.launch { block() }
    }

}