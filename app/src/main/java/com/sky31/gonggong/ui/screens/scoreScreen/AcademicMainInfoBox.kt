package com.sky31.gonggong.ui.screens.scoreScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sky31.gonggong.entity.ScoreData

@Composable
fun AcademicMainInfoBox(
    majorScore: ScoreData?
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(15.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "总览",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
                fontWeight = FontWeight(800),
                letterSpacing = 5.sp
            )

            Spacer(
                modifier = Modifier
                    .padding(top = 5.dp, bottom = 5.dp)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(MaterialTheme.colorScheme.surface)
            )

            AcademicSingleInfoBox(
                name = "绩点",
                value = majorScore?.gpa
            )

            AcademicSingleInfoBoxWithProgress(
                name = "总学分",
                values = majorScore?.totalCredit
            )

            AcademicSingleInfoBoxWithProgress(
                name = "必修学分",
                values = majorScore?.compulsoryCredit
            )

            AcademicSingleInfoBoxWithProgress(
                name = "选修学分",
                values = majorScore?.electiveCredit
            )

            AcademicSingleInfoBoxWithProgress(
                name = "跨学科选修学分",
                values = majorScore?.crossCourseCredit
            )


        }
    }
}

@Composable
fun AcademicSingleInfoBox(
    name: String,
    value: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .width(5.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.primary)
        )

        Text(
            text = name,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = value ?: "-",
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight(700),
            fontSize = 16.sp
        )
    }
}

@Composable
fun AcademicSingleInfoBoxWithProgress(
    name: String,
    values: List<String>?
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(values) {
        if (values != null) {
            val current = values[1].toFloat()
            val total = values[0].toFloat()

            progress.animateTo(
                targetValue = if (current >= total) 1f else current / total,
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = LinearOutSlowInEasing
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 5.dp, bottom = 10.dp, start = 5.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)
                .clip(RoundedCornerShape(8.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .width(5.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )

            Text(
                text = name,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = values?.let { "${values[1]}/${values[0]}" } ?: "-/-",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight(700),
                fontSize = 16.sp
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .fillMaxWidth()
                .height(5.dp)
                .padding(start = 15.dp, end = 15.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .fillMaxWidth(progress.value)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }
}