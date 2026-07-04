package com.kata.berlinclock.domain.usecase

import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState
import com.kata.berlinclock.domain.model.TimeInput
import com.kata.berlinclock.domain.repository.TimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Use case responsible for converting a continuous stream of system time
 * into a [BerlinClockState].
 *
 * It transforms [TimeInput] values from the [TimeRepository] into the
 * Berlin Clock representation.
 */
class ConvertTimeToBerlinClockUseCase @Inject constructor(
    private val repository: TimeRepository
) {

    /**
     * Returns a cold [Flow] emitting the current Berlin Clock state.
     *
     * Each emission is derived from the latest [TimeInput] provided by the
     * [TimeRepository].
     *
     * @return a [Flow] of [BerlinClockState] updated with current time changes.
     */
    fun getCurrentClockState(): Flow<BerlinClockState> {
        return repository.getCurrentTime().map { timeInput ->
            BerlinClockState(
                secondsLamp = getSecondsLamp(timeInput.seconds),
                fiveHourRow = getFiveHourRow(timeInput.hours),
                oneHourRow = getOneHourRow(timeInput.hours),
                fiveMinuteRow = getFiveMinuteRow(timeInput.minutes),
                oneMinuteRow = getOneMinuteRow(timeInput.minutes)
            )
        }
    }

    /**
     * Computes the seconds lamp state.
     *
     * The lamp is:
     * - ON (yellow) when seconds are even
     * - OFF otherwise
     */
    private fun getSecondsLamp(seconds: Int): BerlinClockLamp {
        return BerlinClockLamp.yellow(seconds % 2 == 0)
    }

    /**
     * Computes the five-hour row (top row of hours).
     *
     * Each lamp represents 5 hours.
     * A lamp is lit red when its position is within the full 5-hour blocks.
     *
     * Example:
     * 13 hours → 2 lamps ON (10 hours), 2 OFF
     */
    private fun getFiveHourRow(hours: Int): List<BerlinClockLamp> {
        val onLamps = hours / 5

        return List(4) { index ->
            BerlinClockLamp.red(index < onLamps)
        }
    }

    /**
     * Computes the one-hour row (bottom row of hours).
     *
     * Each lamp represents 1 hour remaining after dividing by 5.
     *
     * Example:
     * 13 hours → 3 lamps ON
     */
    private fun getOneHourRow(hours: Int): List<BerlinClockLamp>{
        val onLamps = hours % 5

        return List(4) { index ->
            BerlinClockLamp.red(index < onLamps)
        }
    }

    /**
     * Computes the five-minute row (middle row).
     *
     * Each lamp represents 5 minutes.
     *
     * Rules:
     * - Yellow lamps represent 5-minute increments
     * - Every 3rd lamp (15, 30, 45 minutes) is red to mark quarters
     * - Remaining lamps are off
     *
     * Example:
     * 23 minutes → 4 lamps ON (first 3 yellow, 4th yellow)
     * 15 minutes → third lamp is red
     */
    private fun getFiveMinuteRow(minutes: Int): List<BerlinClockLamp> {
        val onLamps = minutes / 5
        return List(11) { index ->
            val isOn = index < onLamps
            val isQuarterPosition = (index + 1) % 3 == 0

            when {
                !isOn -> BerlinClockLamp.off()
                isQuarterPosition -> BerlinClockLamp.red(true)
                else -> BerlinClockLamp.yellow(true)
            }
        }
    }

    /**
     * Computes the one-minute row (bottom row).
     *
     * Each lamp represents 1 minute remaining after the five-minute blocks.
     *
     * Example:
     * 23 minutes → 3 lamps ON, 2 OFF
     */
    private fun getOneMinuteRow(minutes: Int): List<BerlinClockLamp> {
        val onLamps = minutes % 5
        return List(4) { index ->
            BerlinClockLamp.yellow(index < onLamps)
        }
    }
}