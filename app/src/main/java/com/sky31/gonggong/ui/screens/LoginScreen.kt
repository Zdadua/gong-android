package com.sky31.gonggong.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sky31.gonggong.R

@Composable
fun LoginScreen(
    navController: NavController
) {

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.login_logo),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
            contentDescription = "Login Logo",
        )

        TextField(
            value = username.value,
            onValueChange = { username.value = it },
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(30.dp)
                .align(Alignment.CenterHorizontally),
        )

        TextField(
            value = password.value,
            onValueChange = { username.value = it },
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(30.dp)
                .align(Alignment.CenterHorizontally),
        )
    }
}