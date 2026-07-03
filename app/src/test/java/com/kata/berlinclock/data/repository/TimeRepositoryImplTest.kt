package com.kata.berlinclock.data.repository

import app.cash.turbine.test
import com.kata.berlinclock.data.datasource.SystemTimeDataSource
import com.kata.berlinclock.domain.model.TimeInput
import com.kata.berlinclock.domain.repository.TimeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TimeRepositoryImplTest {

    private lateinit var dataSource: SystemTimeDataSource
    private lateinit var repository: TimeRepository

    @BeforeEach
    fun setUp() {
        dataSource = mockk()
        repository = TimeRepositoryImpl(dataSource)
    }

    @Test
    fun `getCurrentTime should return flow from dataSource`() = runTest {
        val expectedTime = TimeInput(10, 20, 30)

        every { dataSource.getCurrentTime() } returns flowOf(expectedTime)

        repository.getCurrentTime().test {
            Assertions.assertEquals(expectedTime, awaitItem())
            awaitComplete()
        }
    }
}