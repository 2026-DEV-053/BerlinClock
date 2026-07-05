package com.kata.berlinclock.presentation

import app.cash.turbine.test
import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState
import com.kata.berlinclock.domain.model.TimeInput
import com.kata.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BerlinClockViewModelTest {

    private lateinit var useCase: ConvertTimeToBerlinClockUseCase
    private lateinit var viewModel: BerlinClockViewModel

    @BeforeEach
    fun setUp() {
        useCase = mockk()
        viewModel = BerlinClockViewModel(useCase)
    }

    @Test
    fun `should expose initial clock state`() = runTest {
        val viewModel = BerlinClockViewModel(useCase)

        Assertions.assertEquals(
            BerlinClockState.init(),
            viewModel.clockState.value
        )
    }

    @Test
    fun `startUpdatingClock should emit updated clock state`() = runTest {
        val expectedState = BerlinClockState(
            secondsLamp = BerlinClockLamp.yellow(true),
            fiveHourRow = List(4) { BerlinClockLamp.red(false) },
            oneHourRow = List(4) { BerlinClockLamp.red(false) },
            fiveMinuteRow = List(11) { BerlinClockLamp.off() },
            oneMinuteRow = List(4) { BerlinClockLamp.off() },
            timeInput = TimeInput(0,0,0)
        )

        every {
            useCase.getCurrentClockState()
        } returns flowOf(expectedState)

        viewModel = BerlinClockViewModel(useCase)

        viewModel.clockState.test {
            Assertions.assertEquals(BerlinClockState.init(), awaitItem())

            viewModel.startUpdatingClock()
            advanceUntilIdle()

            Assertions.assertEquals(expectedState, awaitItem())

            cancelAndIgnoreRemainingEvents()
        }
    }

}