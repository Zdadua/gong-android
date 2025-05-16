package com.sky31.gonggong.ui.screens.scoreScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sky31.gonggong.entity.ScoreData
import com.sky31.gonggong.viewmodel.AcademicViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScoreSubScreen(viewModel: AcademicViewModel) {
    val majorScore by viewModel.majorScore
    val majorScoreMap by remember {
        derivedStateOf {
            val result = mutableMapOf<Int, MutableList<ScoreData.ScoreElem>>()

            majorScore?.scores?.forEach { scoreElem ->
                if (!result.containsKey(scoreElem.term)) {
                    result[scoreElem.term] = mutableListOf(scoreElem)
                } else {
                    result[scoreElem.term]!!.add(scoreElem)
                }
            }

            result
        }
    }
    val keys by remember {
        derivedStateOf {
            majorScoreMap.keys.toList().sortedByDescending { it }
        }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { majorScoreMap.size }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp),
    ) {
        VerticalPager(
            state = pagerState,
        ) { page ->
            majorScoreMap[keys[page]]?.let {
                SingleScorePage(keys[page], it)
            }
        }
    }

}