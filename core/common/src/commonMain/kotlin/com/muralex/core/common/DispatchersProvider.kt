package com.muralex.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

interface DispatchersProvider {
    val ui: CoroutineDispatcher
    val io: CoroutineDispatcher
    val default: CoroutineDispatcher

    suspend fun <T> runOnUi(block: suspend CoroutineScope.() -> T): T
    suspend fun <T> runOnIo(block: suspend CoroutineScope.() -> T): T
    suspend fun <T> runInBackground(block: suspend CoroutineScope.() -> T): T

    abstract class Abstract(
        private val uiDispatcher: CoroutineDispatcher,
        private val ioDispatcher: CoroutineDispatcher,
        private val defaultDispatcher: CoroutineDispatcher,
    ) : DispatchersProvider {

        override val ui: CoroutineDispatcher = uiDispatcher
        override val io: CoroutineDispatcher = ioDispatcher
        override val default: CoroutineDispatcher = defaultDispatcher

        override suspend fun <T> runOnUi(block: suspend CoroutineScope. () -> T): T =
            withContext(uiDispatcher, block)

        override suspend fun <T> runOnIo(block: suspend CoroutineScope. () -> T): T =
            withContext(ioDispatcher, block)

        override suspend fun <T> runInBackground(block: suspend CoroutineScope. () -> T): T =
            withContext(defaultDispatcher, block)
    }

    class Base : Abstract(
        uiDispatcher = Dispatchers.Main,
        ioDispatcher = Dispatchers.IO,
        defaultDispatcher = Dispatchers.Default
    )
}