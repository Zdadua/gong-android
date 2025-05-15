package com.sky31.gonggong.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky31.gonggong.MainApplication
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.ExamData
import com.sky31.gonggong.service.CourseService
import com.sky31.gonggong.service.DealCourseService
import com.sky31.gonggong.service.DealExamService
import com.sky31.gonggong.service.DealRequestService
import com.sky31.gonggong.service.ExamService
import com.sky31.gonggong.ui.DataState
import java.time.LocalDate
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel : ViewModel() {
    private val dealCourseService by lazy {
        val courseDao = MainApplication.appDatabase.getCourseDao()
        val service = MainApplication.retrofit.create(CourseService::class.java)
        DealCourseService(service, courseDao)
    }
    private val dealExamService by lazy {
        val examDao = MainApplication.appDatabase.getExamDao()
        val service = MainApplication.retrofit.create(ExamService::class.java)
        DealExamService(service, examDao)
    }

    private val _currentTime = mutableStateOf<LocalDateTime>(LocalDateTime.now())
    val currentTime: State<LocalDateTime> = _currentTime

    private val _isRefreshing = mutableStateOf(false)
    val isRefreshing: State<Boolean> = _isRefreshing

    private val _courseList = mutableStateListOf<CourseData.CourseElem>()
    val courseList: List<CourseData.CourseElem> = _courseList

    private val _examList = mutableStateListOf<ExamData.ExamElem>()
    val examList: List<ExamData.ExamElem> = _examList

    private val _calendar = mutableStateOf<CalendarData?>(null)
    val calendar: State<CalendarData?> = _calendar

    private val _progress = mutableFloatStateOf(-1f)
    val progress: State<Float> = _progress

    private val _courseListState = mutableStateOf<DataState>(DataState.Uninitialized)
    val courseListState = _courseListState

    private val _examListState = mutableStateOf<DataState>(DataState.Uninitialized)
    val examListState = _examListState

    fun refreshCurrentTime() {
        _isRefreshing.value = true
        _currentTime.value = LocalDateTime.now()
        _isRefreshing.value = false
    }

    /**
     * 更新mainScreen所需数据
     */
    suspend fun updateData() {
        _courseListState.value = DataState.Loading
        _examListState.value = DataState.Loading

        when (val courseResult = dealCourseService.getCourses()) {
            is DealRequestService.RequestResult.Success -> {
                _courseListState.value =
                    if (courseResult.code == 200) DataState.Newest else DataState.Expired

                dealCourseService.getTodayCourseList().let {
                    _courseList.clear()
                    _courseList.addAll(it)
                }
            }

            is DealRequestService.RequestResult.Error -> {
                _courseListState.value = DataState.Error
                Log.e(dealCourseService.TAG, "get courses error")
            }
        }

        when (val examResult = dealExamService.getExams()) {
            is DealRequestService.RequestResult.Success -> {
                _examListState.value =
                    if (examResult.code == 200) DataState.Newest else DataState.Expired

                dealExamService.getExamsFromDatabase()?.exams?.let {
                    _examList.clear()
                    _examList.addAll(it)
                }
            }

            is DealRequestService.RequestResult.Error -> {
                _examListState.value = DataState.Error
                Log.e(dealExamService.TAG, "get exams error")
            }
        }

        if (calendar.value == null) {
            val calendarStorage = dealCourseService.getCalendarFromDatabase()
            if (calendarStorage == null) {
                when (dealCourseService.getCalendar()) {
                    is DealRequestService.RequestResult.Success -> {
                        _calendar.value = dealCourseService.getCalendarFromDatabase()
                    }

                    is DealRequestService.RequestResult.Error -> {
                        Log.e(dealCourseService.TAG, "get calendar error")
                    }
                }
            } else {
                _calendar.value = calendarStorage
            }
        }

        _currentTime.value = LocalDateTime.now()
    }

    fun setProgress(progress: Float) {
        _progress.floatValue = progress
    }

    suspend fun getWeek(): Long? {
        if (calendar.value == null) {
            dealCourseService.getCalendar()
            _calendar.value = dealCourseService.getCalendarFromDatabase()
        }

        return _calendar.value?.let {
            val start = LocalDate.parse(it.start)

            (currentTime.value.toLocalDate().toEpochDay() - start.toEpochDay()) / 7 + 1
        }
    }
}