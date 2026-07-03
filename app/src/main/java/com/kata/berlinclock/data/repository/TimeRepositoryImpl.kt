package com.kata.berlinclock.data.repository

import com.kata.berlinclock.data.datasource.SystemTimeDataSource
import com.kata.berlinclock.domain.model.TimeInput
import com.kata.berlinclock.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow

class TimeRepositoryImpl(
    private val dataSource: SystemTimeDataSource
) : TimeRepository {

    override fun getCurrentTime(): Flow<TimeInput> {
        return dataSource.getCurrentTime()
    }
}