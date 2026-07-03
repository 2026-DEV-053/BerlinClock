package com.kata.berlinclock.domain.repository

import com.kata.berlinclock.domain.model.TimeInput
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides a stream of the current time.
 */
interface TimeRepository {
    /**
     * Returns a cold [Flow] that emits the current system time.
     *
     * The flow emits updated [TimeInput] values approximately once per second
     * and continues emitting until the collector cancels the flow.
     *
     * @return a [Flow] of [TimeInput] representing the current time.
     */
    fun getCurrentTime(): Flow<TimeInput>
}