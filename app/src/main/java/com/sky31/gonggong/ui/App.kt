package com.sky31.gonggong.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky31.gonggong.ui.screens.LoginScreen
import com.sky31.gonggong.ui.screens.MainScreen

@Composable
fun App() {

    val navController = rememberNavController()

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(navController)
            }
            composable("main") {
                MainScreen()
            }
        }
    }

}