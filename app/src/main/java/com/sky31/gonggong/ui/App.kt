package com.sky31.gonggong.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun App() {

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {

    }

}