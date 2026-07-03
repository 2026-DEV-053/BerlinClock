package com.kata.berlinclock.data.repository

import com.kata.berlinclock.data.datasource.SystemTimeDataSource
import com.kata.berlinclock.domain.model.TimeInput
import com.kata.berlinclock.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow

/**
 * Default implementation of [TimeRepository].
 *
 * This implementation delegates time retrieval to [SystemTimeDataSource],
 * exposing the current system time as a [Flow] of [TimeInput].
 *
 * @property dataSource The data source responsible for providing the current
 * system time.
 */
class TimeRepositoryImpl(
    private val dataSource: SystemTimeDataSource
) : TimeRepository {

    /**
     * Returns the current time stream provided by the underlying data source.
     */
    override fun getCurrentTime(): Flow<TimeInput> {
        return dataSource.getCurrentTime()
    }
}