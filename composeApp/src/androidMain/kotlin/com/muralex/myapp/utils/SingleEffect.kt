package com.muralex.myapp.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.retain.retain
import com.muralex.myapp.viewmodel.utils.SingleEffect

@Composable
fun SingleEffect(effect: SingleEffect, consume: () -> Unit, perform: suspend () -> Unit) {
    LaunchedEffect(effect.id) {
        if (effect.pending) {
            perform()
            consume()
            println("SingleEffect: consumed")
        }
    }
}


@Composable
fun <T> OnChange(value: T, action: suspend (old: T, new: T) -> Unit) {
    val previous = retain { mutableStateOf(value) }
    LaunchedEffect(value) {
        if (previous.value != value) {
            val old = previous.value
            previous.value = value
            action(old, value)
        }
    }
}

@Composable
fun OnAppear(action: suspend () -> Unit) {
    val appeared = retain { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        if (!appeared.value) {
            appeared.value = true
            action()
        }
    }
}