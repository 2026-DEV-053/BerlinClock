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

    @Test
    fun `should turn seconds lamp off when current second is odd`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(10, 20, 1)
        )

        useCase.getCurrentClockState().test {
            Assertions.assertEquals(
                BerlinClockLamp.yellow(false),
                awaitItem().secondsLamp
            )
            awaitComplete()
        }
    }

    @Test
    fun `should light first five hour lamp when hour is 5`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(5, 0, 0)
        )

        useCase.getCurrentClockState().test {

            Assertions.assertEquals(
                listOf(
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(false),
                    BerlinClockLamp.red(false),
                    BerlinClockLamp.red(false)
                ),
                awaitItem().fiveHourRow
            )

            awaitComplete()
        }
    }

    @Test
    fun `should light all five hour lamps when hour is 20`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(20, 0, 0)
        )

        useCase.getCurrentClockState().test {

            Assertions.assertEquals(
                listOf(
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(true)
                ),
                awaitItem().fiveHourRow
            )

            awaitComplete()
        }
    }

    @Test
    fun `should light first one hour lamp when hour is 1`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(1, 0, 0)
        )

        useCase.getCurrentClockState().test {
            Assertions.assertEquals(
                listOf(
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(false),
                    BerlinClockLamp.red(false),
                    BerlinClockLamp.red(false)
                ),
                awaitItem().oneHourRow
            )

            awaitComplete()
        }
    }

    @Test
    fun `should light all one hour lamps when hour is 4`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(4, 0, 0)
        )

        useCase.getCurrentClockState().test {

            Assertions.assertEquals(
                listOf(
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.red(true)
                ),
                awaitItem().oneHourRow
            )

            awaitComplete()
        }
    }

    @Test
    fun `should light first five minute lamp when minutes is 5`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(0, 5, 0)
        )

        useCase.getCurrentClockState().test {
            Assertions.assertEquals(
                listOf(
                    BerlinClockLamp.yellow(true),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off()
                ),
                awaitItem().fiveMinuteRow
            )

            awaitComplete()
        }
    }

    @Test
    fun `should light third five minute lamp as red when minutes is 15`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(0, 15, 0)
        )

        useCase.getCurrentClockState().test {
            Assertions.assertEquals(
                listOf(
                    BerlinClockLamp.yellow(true),
                    BerlinClockLamp.yellow(true),
                    BerlinClockLamp.red(true),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off(),
                    BerlinClockLamp.off()
                ),
                awaitItem().fiveMinuteRow
            )

            awaitComplete()
        }
    }

    @Test
    fun `should turn off all five minute lamps when minutes is zero`() = runTest {
        every { repository.getCurrentTime() } returns flowOf(
            TimeInput(0, 0, 0)
        )

        useCase.getCurrentClockState().test {

            Assertions.assertTrue(
                awaitItem().fiveMinuteRow.all { !it.isOn }
            )

            awaitComplete()
        }
    }
}