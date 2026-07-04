package com.kata.berlinclock.domain.usecase

import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState
import com.kata.berlinclock.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ConvertTimeToBerlinClockUseCase @Inject constructor(
    private val repository: TimeRepository
) {
    fun getCurrentClockState(): Flow<BerlinClockState> {
        return repository.getCurrentTime().map { timeInput ->
            BerlinClockState(
                secondsLamp = getSecondsLamp(timeInput.seconds)
            )
        }
    }

    private fun getSecondsLamp(seconds: Int): BerlinClockLamp {
        return BerlinClockLamp.yellow(seconds % 2 == 0)
    }
}