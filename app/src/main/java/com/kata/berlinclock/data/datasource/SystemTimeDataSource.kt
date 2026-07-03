package com.kata.berlinclock.data.datasource

import com.kata.berlinclock.domain.model.TimeInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import kotlin.time.Duration.Companion.milliseconds

/**
 * Data source that continuously emits the current system time.
 *
 * The returned [Flow] emits a new [TimeInput] approximately once every second.
 */
class SystemTimeDataSource {
    /**
     * Returns a cold [Flow] that emits the current system time indefinitely.
     *
     * Every emission contains the current hour, minute, and second from the
     * system calendar.
     *
     * @return a [Flow] emitting the current system time once per second.
     */
    fun getCurrentTime(): Flow<TimeInput> = flow {
        while (true) {
            val calendar = Calendar.getInstance()
            val time = TimeInput(
                hours = calendar.get(Calendar.HOUR_OF_DAY),
                minutes = calendar.get(Calendar.MINUTE),
                seconds = calendar.get(Calendar.SECOND)
            )
            emit(time)
            delay((1000 - (System.currentTimeMillis() % 1000)).milliseconds)
        }
    }
}