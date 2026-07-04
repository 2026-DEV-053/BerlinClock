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
                secondsLamp = getSecondsLamp(timeInput.seconds),
                fiveHourRow = getFiveHourRow(timeInput.hours),
                oneHourRow = getOneHourRow(timeInput.hours),
                fiveMinuteRow = getFiveMinuteRow()
            )
        }
    }

    private fun getSecondsLamp(seconds: Int): BerlinClockLamp {
        return BerlinClockLamp.yellow(seconds % 2 == 0)
    }

    private fun getFiveHourRow(hours: Int): List<BerlinClockLamp> {
        val onLamps = hours / 5

        return List(4) { index ->
            BerlinClockLamp.red(index < onLamps)
        }
    }

    private fun getOneHourRow(hours: Int): List<BerlinClockLamp>{
        val onLamps = hours % 5

        return List(4) { index ->
            BerlinClockLamp.red(index < onLamps)
        }
    }

    private fun getFiveMinuteRow(): List<BerlinClockLamp>{
        return listOf(
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
        )
    }
}