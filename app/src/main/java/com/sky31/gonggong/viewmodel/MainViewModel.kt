package com.sky31.gonggong.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.entity.ExamData
import com.sky31.gonggong.model.state.DataState
import com.sky31.gonggong.service.AppRepository
import com.sky31.gonggong.service.DealRequestService
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class MainViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val dealCourseService get() = repo.getDealCourseService()
    private val dealExamService = repo.getDealExamService()

    private val _currentTime = mutableStateOf<LocalDateTime>(LocalDateTime.now())
    val currentTime: State<LocalDateTime> = _currentTime

    private var _courseList = mutableStateOf<List<CourseData.CourseElem>>(emptyList())
    val courseList: State<List<CourseData.CourseElem>> = _courseList

    private val _completedCourseNum = mutableIntStateOf(0)
    val completedCourseNum: State<Int> = _completedCourseNum

    private val _examList = mutableStateOf<List<ExamData.ExamElem>>(emptyList())
    val examList: State<List<ExamData.ExamElem>> = _examList

    private val _calendar = mutableStateOf<CalendarData?>(null)
    val calendar: State<CalendarData?> = _calendar

    private val _progression = mutableFloatStateOf(-1f)
    val progression: State<Float> = _progression

    fun refreshCurrentTime() {
        _currentTime.value = LocalDateTime.now()
    }

    /**
     * 更新今日课程表
     *
     * @param init 初始化函数
     * @param finished 数据获取结束函数
     */
    suspend fun updateCourseList(
        init: () -> Unit,
        finished: (state: DataState) -> Unit
    ) {
        init()

        when (val courseResult = dealCourseService.getCourses()) {
            is DealRequestService.RequestResult.Success -> {
                val state = if (courseResult.code == 200) DataState.Newest else DataState.Expired
                finished(state)

                _courseList.value = dealCourseService.getTodayCourseList()
            }

            is DealRequestService.RequestResult.Error -> {
                finished(DataState.Error)
                Log.e(dealCourseService.TAG, "get courses error")
            }
        }
    }

    /**
     * 更新考试安排
     *
     * @param init 初始化函数
     * @param finished 数据获取结束函数
     */
    suspend fun updateExamList(
        init: () -> Unit,
        finished: (state: DataState) -> Unit
    ) {
        init()

        when (val examResult = dealExamService.getExams()) {
            is DealRequestService.RequestResult.Success -> {
                val state = if (examResult.code == 200) DataState.Newest else DataState.Expired
                finished(state)

                dealExamService.getExamsFromDatabase()?.exams?.let {
                    _examList.value = it
                }
            }

            is DealRequestService.RequestResult.Error -> {
                finished(DataState.Error)
                Log.e(dealExamService.TAG, "get exams error")
            }
        }
    }

    /**
     * 更新校历
     */
    suspend fun updateCalendar() {
        if (_calendar.value == null) {
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

    fun setProgression(progression: Float) {
        _progression.floatValue = progression
    }

    fun setCompletedNum(num: Int) {
        _completedCourseNum.intValue = num
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