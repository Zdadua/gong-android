package com.sky31.gonggong.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.sky31.gonggong.entity.CalendarData
import com.sky31.gonggong.entity.CourseData
import com.sky31.gonggong.model.state.DataState
import com.sky31.gonggong.service.AppRepository
import com.sky31.gonggong.service.DealRequestService
import com.sky31.gonggong.utils.TimeUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class CourseViewModel @Inject constructor(
    private val repo: AppRepository
) : ViewModel() {
    private val dealCourseService get() = repo.getDealCourseService()



    private val _courseMap = mutableMapOf<String, List<CourseData.CourseElem>>()
    private val courseMap: Map<String, List<CourseData.CourseElem>> = _courseMap

    private val _calendar = mutableStateOf<CalendarData?>(null)
    val calendar: State<CalendarData?> = _calendar

    private val _currentTime = mutableStateOf<LocalDateTime>(LocalDateTime.now())
    private val currentTime: State<LocalDateTime> = _currentTime

    private val _curWeekNum = mutableLongStateOf(0)
    val curWeekNum: State<Long> = _curWeekNum

    private val _courseTableState = mutableStateOf<DataState>(DataState.Expired)
    val courseTableState: State<DataState> = _courseTableState

    /**
     * 获取当前时间周次
     */
    fun getWeekNum() {
        _curWeekNum.longValue = if (calendar.value != null) (currentTime.value.toLocalDate()
            .toEpochDay() - LocalDate.parse(calendar.value!!.start).toEpochDay()) / 7 + 1 else 0
    }

    /**
     * 更新课程表和校历数据
     */
    suspend fun updateData() {
        _courseTableState.value = DataState.Loading

        when (val courseResult = dealCourseService.getCourses()) {
            is DealRequestService.RequestResult.Success -> {
                // 根据状态码修改状态
                _courseTableState.value =
                    if (courseResult.code == 200) DataState.Newest else DataState.Expired

                // 若room中存在，则更新
                dealCourseService.getCourseMap()?.let {
                    _courseMap.clear()
                    _courseMap.putAll(it)
                }
            }

            is DealRequestService.RequestResult.Error -> {
                _courseTableState.value = DataState.Error
                Log.e(dealCourseService.TAG, "get course error")
            }
        }

        /*
         * 获取校历数据
         * 若viewModel中存在，则不从room中获取
         * 若room中存在，则从room中获取
         * 否则发起网络请求
         */
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

    /**
     * 获取对应周次的课程表
     *
     * @param weekNum 周次
     */
    fun getWeekCourseMap(weekNum: Long): Map<String, List<CourseData.CourseElem>> {
        val resultMap = mutableMapOf<String, List<CourseData.CourseElem>>()

        courseMap.forEach { (weekday, courseElems) ->
            val courseList = mutableListOf<CourseData.CourseElem>()
            courseElems.forEach { courseElem ->
                if (TimeUtil.isInThisWeek(weekNum, courseElem)) {
                    courseList.add(courseElem)
                }
            }
            courseList.sortBy { it.startTime }
            resultMap[weekday] = courseList
        }

        return resultMap
    }
}