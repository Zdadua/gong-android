package com.sky31.gonggong.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky31.gonggong.entity.ClassroomData
import com.sky31.gonggong.service.AppRepository
import com.sky31.gonggong.service.DealRequestService
import com.sky31.gonggong.ui.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ClassroomViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val dealClassroomService get() = repo.getDealClassroomService()

    private val _todayClassroomMap = mutableStateMapOf<String, List<ClassroomData.ClassroomInfo>>()
    val todayClassroomMap: Map<String, List<ClassroomData.ClassroomInfo>> = _todayClassroomMap

    private val _todayDate = mutableStateOf<LocalDate?>(null)
    val todayDate: State<LocalDate?> = _todayDate

    private val _tomorrowClassroomMap =
        mutableStateMapOf<String, List<ClassroomData.ClassroomInfo>>()
    val tomorrowClassroomMap: Map<String, List<ClassroomData.ClassroomInfo>> = _tomorrowClassroomMap

    private val _tomorrowDate = mutableStateOf<LocalDate?>(null)
    val tomorrowDate: State<LocalDate?> = _tomorrowDate

    private var _todayClassroomState = mutableStateOf<DataState>(DataState.Uninitialized)
    val todayClassroomState: State<DataState> = _todayClassroomState

    private var _tomorrowClassroomState = mutableStateOf<DataState>(DataState.Uninitialized)
    val tomorrowClassroomState: State<DataState> = _tomorrowClassroomState

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun updateData() {
        _todayClassroomState.value = DataState.Loading

        when (val todayResult = dealClassroomService.getTodayClassroom()) {
            is DealRequestService.RequestResult.Success -> {
                _todayClassroomState.value =
                    if (todayResult.code == 200) DataState.Newest else DataState.Expired

                dealClassroomService.getTodayClassroomFromDatabase()?.let {
                    _todayClassroomMap.clear()
                    _todayClassroomMap.putAll(it.classrooms)

                    _todayDate.value = LocalDate.parse(it.date)
                }
            }

            is DealRequestService.RequestResult.Error -> {
                _todayClassroomState.value = DataState.Error
                Log.e(dealClassroomService.TAG, "get todayClassroom error")
            }
        }

        when (val tomorrowResult = dealClassroomService.getTomorrowClassroom()) {
            is DealRequestService.RequestResult.Success -> {
                _tomorrowClassroomState.value =
                    if (tomorrowResult.code == 200) DataState.Newest else DataState.Expired

                dealClassroomService.getTomorrowClassroomFromDatabase()?.let {
                    _tomorrowClassroomMap.clear()
                    _tomorrowClassroomMap.putAll(it.classrooms)

                    _tomorrowDate.value = LocalDate.parse(it.date)
                }
            }

            is DealRequestService.RequestResult.Error -> {
                _tomorrowClassroomState.value = DataState.Error
                Log.e(dealClassroomService.TAG, "get tomorrowClassroom error")
            }
        }
    }
}