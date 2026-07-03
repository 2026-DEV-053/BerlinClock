package com.kata.berlinclock.data.datasource

import com.kata.berlinclock.domain.model.TimeInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds

class SystemTimeDataSource {
    fun getCurrentTime(): Flow<TimeInput> = flow {
        while (true) {
            val calendar = Calendar.getInstance()
            val time = TimeInput(
                hours = calendar.get(Calendar.HOUR_OF_DAY)
            )
            emit(time)
            delay((1000 - (System.currentTimeMillis() % 1000)).milliseconds)
        }
    }
}