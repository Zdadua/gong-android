package com.sky31.gonggong

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sky31.gonggong.utils.TimeUtil
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDateTime

@RunWith(AndroidJUnit4::class)
class TimeUtilTest {

    @Test
    fun testGetCourseState() {
        val dateTime = LocalDateTime.parse("2023-05-01")

        var state = TimeUtil.getCourseState(dateTime, 1, 2)
        assert(state == TimeUtil.CourseState.During)

        state = TimeUtil.getCourseState(dateTime, 2, 2)
        assert(state == TimeUtil.CourseState.Before)
    }
}