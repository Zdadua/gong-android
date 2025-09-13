package com.sky31.gonggong.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky31.gonggong.ui.layout.MainLayout
import com.sky31.gonggong.ui.screens.courseScreen.CourseScreen
import com.sky31.gonggong.ui.screens.loginScreen.LoginScreen
import com.sky31.gonggong.ui.screens.scoreScreen.AcademicScreen
import com.sky31.gonggong.viewmodel.AuthState
import com.sky31.gonggong.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()

    Box(
        modifier = Modifier
    ) {
        AppProvider(
            navController = navController,
        ) {
            NavHost(
                navController = navController,
                startDestination = if (authViewModel.authState.value is AuthState.Authenticated) "main" else "login"
            ) {
                composable("login") {
                    LoginScreen(navController)
                }
                composable("main") {
                    MainLayout()
                }
                composable("courseScreen") {
                    CourseScreen(navController)
                }
                composable("academicScreen") {
                    AcademicScreen(navController)
                }
            }
        }
    }
}