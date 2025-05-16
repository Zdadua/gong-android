package com.sky31.gonggong.ui.screens.scoreScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.ui.theme.LocalThemeColor
import com.sky31.gonggong.viewmodel.AcademicViewModel

@Composable
fun MainInfoSubScreen(viewModel: AcademicViewModel) {
    val majorScore by viewModel.majorScore
    val compulsoryRank by viewModel.compulsoryRank
    val totalRank by viewModel.totalRank

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalThemeColor.current.backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp, start = 10.dp, end = 10.dp)
        ) {
            majorScore?.let { AcademicMainInfoBox(it) }

            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(LocalThemeColor.current.boxColorPrimary)
                    .padding(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "英语",
                        color = LocalThemeColor.current.textPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800),
                        letterSpacing = 5.sp
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(LocalThemeColor.current.borderColor)
                    )

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            AcademicSingleInfoBox(
                                name = "CET4",
                                value = majorScore?.cet4
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            AcademicSingleInfoBox(
                                name = "CET6",
                                value = majorScore?.cet6
                            )
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp))
                    .background(LocalThemeColor.current.boxColorPrimary)
                    .padding(15.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "排名",
                        color = LocalThemeColor.current.textPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(800),
                        letterSpacing = 5.sp
                    )

                    Spacer(
                        modifier = Modifier
                            .padding(top = 5.dp, bottom = 5.dp)
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(LocalThemeColor.current.borderColor)
                    )

                    AcademicSingleInfoBox(
                        name = "必修排名",
                        value = compulsoryRank?.majorRank.toString()
                    )

                    AcademicSingleInfoBox(
                        name = "必修班级排名",
                        value = compulsoryRank?.classRank.toString()
                    )

                    AcademicSingleInfoBox(
                        name = "总排名",
                        value = totalRank?.majorRank.toString()
                    )

                    AcademicSingleInfoBox(
                        name = "班级排名",
                        value = totalRank?.classRank.toString()
                    )
                }
            }
        }
    }
}