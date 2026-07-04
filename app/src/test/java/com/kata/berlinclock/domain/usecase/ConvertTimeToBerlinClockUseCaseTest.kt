package com.kata.berlinclock.domain.usecase

import app.cash.turbine.test
import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.TimeInput
import com.kata.berlinclock.domain.repository.TimeRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ConvertTimeToBerlinClockUseCaseTest {

    private lateinit var repository: TimeRepository
    private lateinit var useCase: ConvertTimeToBerlinClockUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk()
        useCase = ConvertTimeToBerlinClockUseCase(repository)
    }

    @Test
    fun `should turn seconds lamp on when current second is even`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(10, 20, 0)
        )

        useCase.getCurrentClockState().test {

            Assertions.assertEquals(
                BerlinClockLamp.yellow(true),
                awaitItem().secondsLamp
            )
            awaitComplete()
        }
    }
}