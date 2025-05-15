package com.sky31.gonggong.ui.screens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.ui.theme.Orange01

/**
 * mainScreen左侧栏
 *
 * @param closeDrawer 关闭drawer
 * @param logout 退出登录
 */
@Composable
fun MainScreenDrawer(closeDrawer: () -> Unit, logout: () -> Unit) {

    Scaffold(
        containerColor = Color.Transparent,
        modifier = Modifier
            .width(250.dp)
            .padding(end = 5.dp)
            .clip(RoundedCornerShape(topEnd = 15.dp, bottomEnd = 15.dp))
            .background(LocalThemeColor.current.backgroundColor),
        bottomBar = {
            BottomAppBar(
                containerColor = Orange01,
                modifier = Modifier
                    .height(65.dp)
                    .padding(start = 10.dp, end = 10.dp, bottom = 15.dp, top = 5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable { logout() }
            ) {
                Text(
                    text = "退出登录",
                    color = Color.White,
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text("drawer content")

            Button(
                modifier = Modifier
                    .width(50.dp)
                    .height(20.dp),
                onClick = {
                    closeDrawer()
                }
            ) { }
        }
    }
}