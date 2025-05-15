package com.sky31.gonggong.ui.screens.loginScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sky31.gonggong.R
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.ui.theme.Orange01
import com.sky31.gonggong.viewmodel.AuthState
import com.sky31.gonggong.viewmodel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val scope = rememberCoroutineScope()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var clickable by remember { mutableStateOf<LoginButtonState>(LoginButtonState.Clickable) }
    var alertVisible by remember { mutableStateOf(false) }
    var alertText by remember { mutableStateOf("") }

    val animatedColor by animateColorAsState(
        targetValue = if (clickable == LoginButtonState.Clickable) Orange01 else LocalThemeColor.current.boxColorPrimary,
        animationSpec = tween(200),
        label = "color")

    val authState by authViewModel.authState.collectAsState()

    val passwordVisible = remember { mutableStateOf(false) }
    val passwordIconId = remember(passwordVisible.value) {
        if (passwordVisible.value) R.drawable.password_visible else R.drawable.password_invisible
    }

    LaunchedEffect(authState) {
        when(authState) {
            is AuthState.Authenticated -> {
                navController.navigate("main")
            }
            is AuthState.Loading -> {

            }
            is AuthState.Error -> {
                alertText = (authState as AuthState.Error).message
                alertVisible = true
                delay(1500)
                alertVisible = false
                authViewModel.resetAuthState()
            }
            else -> {}
        }
    }

    LaunchedEffect(username, password) {
        clickable = if(username == "" || password == "") {
            LoginButtonState.UnClickable
        } else {
            LoginButtonState.Clickable
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp)
    ) {
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
        ) {
            this@Column.AnimatedVisibility(
                visible = alertVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(30.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(Orange01)
                            .padding(top = 6.dp, bottom = 6.dp, start = 25.dp, end = 25.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "is $alertText",
                            color = Color.White
                            )
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier
                .height(40.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.login_logo),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(200.dp),
            contentDescription = "Login Logo",
        )

        Spacer(
            modifier = Modifier
                .height(40.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(45.dp)
                .align(Alignment.CenterHorizontally)
                .border(
                    width = 1.dp,
                    color = Color(0xFF969696),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(start = 5.dp, end = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                decorationBox = { innerTextField ->
                    Box {
                        if (username.isEmpty()) {
                            Text("用户名", color = LocalThemeColor.current.textSecondary)
                        }
                        innerTextField()
                    }
                },
                value = username,
                onValueChange = {
                    username = it
                },
            )
        }

        Spacer(
            modifier = Modifier
                .height(15.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(.7f)
                .height(45.dp)
                .align(Alignment.CenterHorizontally)
                .border(
                    width = 1.dp,
                    color = Color(0xFF969696),
                    shape = RoundedCornerShape(30.dp)
                )
                .padding(start = 5.dp, end = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 10.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                ),
                visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Password
                ),
                decorationBox = { innerTextField ->
                    Box {
                        if (password.isEmpty()) {
                            Text("密码", color = LocalThemeColor.current.textSecondary)
                        }
                        innerTextField()
                    }
                },
                value = password,
                onValueChange = {
                    password = it
                },
            )

            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable {
                        passwordVisible.value = !passwordVisible.value
                    },
            ) {
                Image(
                    painter = painterResource(id = passwordIconId),
                    contentDescription = "Login Logo",
                )
            }
        }

        Spacer(modifier = Modifier
            .height(80.dp)
        )

        Button(
            modifier = Modifier
                .width(170.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(
                containerColor = animatedColor,
                contentColor = Color.White
            ),
            onClick = {
                if(clickable == LoginButtonState.Clickable) {
                    scope.launch {
                        authViewModel.login(username, password)
                    }
                }
            }
        ) {
            Text(text = "登录")
        }

    }
}

/**
 * 登录按钮状态
 */
sealed class LoginButtonState {
    data object Clickable: LoginButtonState()
    data object UnClickable: LoginButtonState()
    data object Loading: LoginButtonState()
}