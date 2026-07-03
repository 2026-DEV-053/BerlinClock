package com.kata.berlinclock.domain.repository

import com.kata.berlinclock.domain.model.TimeInput
import kotlinx.coroutines.flow.Flow

interface TimeRepository {
    fun getCurrentTime(): Flow<TimeInput>
}