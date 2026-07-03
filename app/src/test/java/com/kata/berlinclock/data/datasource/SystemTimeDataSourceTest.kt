package com.kata.berlinclock.data.datasource

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SystemTimeDataSourceTest {
    private val dataSource = SystemTimeDataSource()

    @Test
    fun `getCurrentTime emits valid hours`() = runTest {
        dataSource.getCurrentTime().test {
            val time = awaitItem()

            Assertions.assertTrue(time.hours in 0..23)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getCurrentTime emits valid hours and minutes`() = runTest {
        dataSource.getCurrentTime().test {
            val time = awaitItem()

            Assertions.assertTrue(time.hours in 0..23 && time.minutes in 0..59)

            cancelAndIgnoreRemainingEvents()
        }
    }
}