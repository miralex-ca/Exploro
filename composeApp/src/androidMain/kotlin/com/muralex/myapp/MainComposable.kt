package com.muralex.myapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.muralex.myapp.navigation.Router
import com.muralex.myapp.theme.AppTheme
import com.muralex.myapp.viewmodel.DKMPViewModel


@Composable
fun MainComposable(
    model: DKMPViewModel
) {
    val dkmpNav = model.navigation

    AppTheme {
        dkmpNav.Router()
    }


}