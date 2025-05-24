package com.sky31.gonggong.ui.screens.classroomScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.sky31.gonggong.R
import com.sky31.gonggong.ui.theme.Orange01

@Composable
fun ClassroomTopBar(navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Orange01)
            .padding(start = 15.dp, end = 15.dp)
    ) {
        // 放置按钮
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp)
                    .clickable {
                        navController.navigate("main")
                    },
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = "back_arrow",
            )
        }
        // title
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "空教室查询",
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}