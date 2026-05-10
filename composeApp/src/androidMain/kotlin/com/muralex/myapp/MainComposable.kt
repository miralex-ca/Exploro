package com.muralex.myapp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.muralex.myapp.navigation.Router
import com.muralex.myapp.viewmodel.DKMPViewModel


@Composable
fun MainComposable(
    model: DKMPViewModel
) {
    val dkmpNav = model.navigation

    MaterialTheme {
        dkmpNav.Router()
    }
}