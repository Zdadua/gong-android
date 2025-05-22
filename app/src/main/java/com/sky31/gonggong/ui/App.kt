package com.sky31.gonggong.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sky31.gonggong.ui.screens.classroomScreen.ClassroomScreen
import com.sky31.gonggong.ui.screens.courseScreen.CourseScreen
import com.sky31.gonggong.ui.screens.loginScreen.LoginScreen
import com.sky31.gonggong.ui.screens.mainScreen.MainScreen
import com.sky31.gonggong.ui.screens.scoreScreen.AcademicScreen
import com.sky31.gonggong.ui.theme.DarkColor
import com.sky31.gonggong.ui.theme.LightColor
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun App() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = hiltViewModel()
    val themeColor = if(isSystemInDarkTheme()) DarkColor else LightColor

    Box(
        modifier = Modifier
    ) {
        CompositionLocalProvider(LocalThemeColor provides themeColor) {
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
                composable("courseScreen") {
                    CourseScreen(navController)
                }
                composable("academicScreen") {
                    AcademicScreen(navController)
                }
                composable("classroomScreen") {
                    ClassroomScreen(navController)
                }
            }
        }
    }
}