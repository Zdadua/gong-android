package com.sky31.gonggong.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky31.gonggong.entity.RankData
import com.sky31.gonggong.entity.ScoreData
import com.sky31.gonggong.service.AppRepository
import com.sky31.gonggong.service.DealRequestService
import com.sky31.gonggong.ui.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AcademicViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val dealAcademicService get() = repo.getDealAcademicService()
    private val _majorScore = mutableStateOf<ScoreData?>(null)
    val majorScore = _majorScore

    private val _compulsoryRank = mutableStateOf<RankData?>(null)
    val compulsoryRank = _compulsoryRank

    private val _totalRank = mutableStateOf<RankData?>(null)
    val totalRank = _totalRank

    private val majorScoreState = mutableStateOf<DataState>(DataState.Uninitialized)
    private val compulsoryRankState = mutableStateOf<DataState>(DataState.Uninitialized)
    private val totalRankState = mutableStateOf<DataState>(DataState.Uninitialized)

    suspend fun updateData() {
        when (val majorScoreResult = dealAcademicService.getScores()) {
            is DealRequestService.RequestResult.Success -> {
                majorScoreState.value =
                    if (majorScoreResult.code == 200) DataState.Newest else DataState.Expired
                _majorScore.value = dealAcademicService.getMajorScoreFromDatabase()
            }

            is DealRequestService.RequestResult.Error -> {
                majorScoreState.value = DataState.Error
            }
        }

        when (val compulsoryRankResult = dealAcademicService.getCompulsoryRank()) {
            is DealRequestService.RequestResult.Success -> {
                compulsoryRankState.value =
                    if (compulsoryRankResult.code == 200) DataState.Newest else DataState.Expired
                _compulsoryRank.value = dealAcademicService.getCompulsoryRankFromDatabase()

                println(dealAcademicService.getCompulsoryRankFromDatabase()?.majorRank)
            }

            is DealRequestService.RequestResult.Error -> {
                compulsoryRankState.value = DataState.Error
            }
        }

        when (val totalRankResult = dealAcademicService.getTotalRank()) {
            is DealRequestService.RequestResult.Success -> {
                totalRankState.value =
                    if (totalRankResult.code == 200) DataState.Newest else DataState.Expired
                _totalRank.value = dealAcademicService.getTotalRankFromDatabase()
            }

            is DealRequestService.RequestResult.Error -> {
                totalRankState.value = DataState.Error
            }
        }
    }
}