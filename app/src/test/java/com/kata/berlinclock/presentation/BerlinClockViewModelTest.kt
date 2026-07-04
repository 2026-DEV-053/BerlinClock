package com.kata.berlinclock.presentation

import com.kata.berlinclock.domain.model.BerlinClockState
import com.kata.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
}