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
import com.sky31.gonggong.ui.screens.loginScreen.LoginScreen
import com.sky31.gonggong.ui.screens.mainScreen.MainScreen
import com.sky31.gonggong.viewmodel.AuthViewModel

@Composable
fun App() {

    val navController = rememberNavController()
    val authViewModel = AuthViewModel()

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(navController, authViewModel)
            }
            composable("main") {
                MainScreen(navController, authViewModel)
            }
        }
    }

}