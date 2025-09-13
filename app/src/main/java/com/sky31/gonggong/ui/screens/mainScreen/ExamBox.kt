package com.sky31.gonggong.ui.screens.mainScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.R
import com.sky31.gonggong.entity.ExamData
import com.sky31.gonggong.utils.TimeUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * mainScreen考试容器
 *
 * @param exam 考试数据
 * @param currentTime 当前时间
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ExamBox(exam: ExamData.ExamElem, currentTime: LocalDateTime) {
    val countdown by remember(currentTime, exam) {
        if (exam.startTime.isNotEmpty()) {
            val examDateTime =
                LocalDateTime.parse(exam.startTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            mutableStateOf<Long?>(
                examDateTime.toLocalDate().toEpochDay() - currentTime.toLocalDate().toEpochDay()
            )
        } else {
            mutableStateOf<Long?>(null)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 5.dp, bottom = 5.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(.6f),
                text = exam.name,
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier
                    .weight(.4f),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(modifier = Modifier.weight(1f))

                if (countdown != null) {
                    if (countdown!! > 0) {
                        Text(
                            text = "$countdown",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.primary,
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Text(
                            text = "天",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else if (countdown!!.toInt() == 0) {
                        Text(
                            text = "今天",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    } else {
                        Text(
                            text = "已结束",
                            fontSize = 20.sp,
                            fontWeight = FontWeight(600),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_location_on_24),
                    contentDescription = "location",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(16.dp)
                )

                Text(
                    modifier = Modifier
                        .weight(1f),
                    text = if (exam.location == "") "无地点安排" else exam.location,
                    fontSize = 12.sp,
                    fontWeight = FontWeight(600),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_access_time_filled_24),
                    contentDescription = "time",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(16.dp)
                )

                if (exam.startTime.isNotEmpty()) {
                    val startTime = LocalDateTime.parse(exam.startTime)
                        .let { TimeUtil.CustomTime(it.hour, it.minute) }
                    val endTime = LocalDateTime.parse(exam.endTime)
                        .let { TimeUtil.CustomTime(it.hour, it.minute) }

                    Text(
                        text = "${TimeUtil.customTimeToString(startTime)}-${
                            TimeUtil.customTimeToString(
                                endTime
                            )
                        }",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Text(
                        text = "无时间安排",
                        fontSize = 12.sp,
                        fontWeight = FontWeight(600),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}