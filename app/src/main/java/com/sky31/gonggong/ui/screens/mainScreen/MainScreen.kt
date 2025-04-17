package com.sky31.gonggong.ui.screens.mainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sky31.gonggong.viewmodel.AuthState
import com.sky31.gonggong.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun MainScreen(navController: NavController, authViewModel: AuthViewModel) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when(authState) {
            is AuthState.Unauthenticated -> {
                navController.navigate("login")
            }
            else -> {}
        }
    }

    Column() {
        Text("Main Screen")

        Button(
            modifier = Modifier
                .height(20.dp)
                .width(20.dp),
            onClick = {
                scope.launch {
                    authViewModel.logout()
                }
            }
        ) { }
    }
}