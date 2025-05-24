package com.sky31.gonggong.ui.screens.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.ui.theme.Orange01
import com.sky31.gonggong.viewmodel.AuthState
import com.sky31.gonggong.viewmodel.AuthViewModel
import com.sky31.gonggong.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(navController: NavController, authViewModel: AuthViewModel) {

    val scope = rememberCoroutineScope()
    val viewModel: MainViewModel = hiltViewModel()

    val authState by authViewModel.authState.collectAsState()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val pagerState = rememberPagerState(pageCount = { 2 })

    LaunchedEffect(authState) {
        when(authState) {
            is AuthState.Unauthenticated -> {
                navController.navigate("login")
            }
            is AuthState.Authenticated -> {
                viewModel.updateData()
            }
            else -> {}
        }
    }

    // 左侧栏
    ModalNavigationDrawer(
        gesturesEnabled = false,
        drawerState = drawerState,
        drawerContent = {
            MainScreenDrawer({ scope.launch { drawerState.close() } }) { scope.launch { authViewModel.logout() } }
        }
    ) {
        Scaffold(
            bottomBar = {
                // TODO 添加跳转
                BottomAppBar(
                    containerColor = Orange01,
                    contentColor = Color.White,
                    modifier = Modifier
                        .height(65.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "Bottom app bar",
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalThemeColor.current.backgroundColor)
                    .padding(innerPadding),
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    ) {
                        MainInfoBox(viewModel)

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(50.dp))
                                    .background(Color(0xFF2FB0BE))
                                    .clickable {
                                        scope.launch {
                                            drawerState.open()
                                        }
                                    }
                            ) {

                            }

                            Box(
                                modifier = Modifier
                                    .padding(start = 8.dp, top = 8.dp)
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(LocalThemeColor.current.boxColorPrimary)
                            ) {

                            }
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(LocalThemeColor.current.boxColorPrimary)
                    )

                    // 禁用overScroll
                    CompositionLocalProvider(
                        LocalOverscrollConfiguration provides null
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                                .clip(RoundedCornerShape(15.dp)),
                            flingBehavior = PagerDefaults.flingBehavior(
                                state = pagerState,
                                snapAnimationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            ),
                            pageSpacing = 5.dp,
                        ) { page ->
                            if (page == 0)
                                CourseSubScreen(viewModel)
                            else
                                ExamSubScreen(viewModel)
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .padding(top = 12.dp, bottom = 12.dp)
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(LocalThemeColor.current.boxColorPrimary)
                    )

                    // TODO 跳转 课程表、成绩单、空教室
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .width(30.dp)
                                .aspectRatio(1f),
                            onClick = { navController.navigate("courseScreen") }
                        ) { }

                        Button(
                            modifier = Modifier
                                .width(30.dp)
                                .aspectRatio(1f),
                            onClick = { navController.navigate("academicScreen") }
                        ) { }

                        Button(
                            modifier = Modifier
                                .width(30.dp)
                                .aspectRatio(1f),
                            onClick = { navController.navigate("classroomScreen") }
                        ) { }
                    }
                }
            }
        }
    }
}