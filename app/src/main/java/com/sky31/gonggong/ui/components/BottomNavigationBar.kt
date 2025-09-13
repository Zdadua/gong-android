package com.sky31.gonggong.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.sky31.gonggong.R

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val toHome = fun() {
        if (currentRoute !== "home") {
            navController.navigate("home")
        }
    }

    val toEmptyClassroom = fun() {
        if (currentRoute !== "emptyClassroom") {
            navController.navigate("emptyClassroom")
        }
    }

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White,
        modifier = Modifier
            .height(75.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BottomNavigationItem(
                    resourceId = R.drawable.home,
                    title = "首页",
                    onClick = toHome
                )

                BottomNavigationItem(
                    resourceId = R.drawable.classroom,
                    title = "空教室",
                    onClick = toEmptyClassroom
                )

                BottomNavigationItem(
                    resourceId = R.drawable.home,
                    title = "社交圈",
                    onClick = toHome
                )
            }
        }
    }
}