package com.kata.berlinclock.domain.model

/**
 * Represents the full Berlin Clock state at a given moment in time.
 *
 * It contains all lamp rows required to display the Berlin Clock:
 * seconds, hours, and minutes.
 */
data class BerlinClockState(
    val secondsLamp: BerlinClockLamp,
    val fiveHourRow: List<BerlinClockLamp>,
    val oneHourRow: List<BerlinClockLamp>,
    val fiveMinuteRow: List<BerlinClockLamp>,
    val oneMinuteRow: List<BerlinClockLamp>
) {
    companion object {

        /**
         * Creates an initial empty Berlin Clock state with the seconds lamp off
         * and all lamp rows uninitialized.
         */
        fun init() = BerlinClockState(
            secondsLamp = BerlinClockLamp.off(),
            fiveHourRow = emptyList(),
            oneHourRow = emptyList(),
            fiveMinuteRow = emptyList(),
            oneMinuteRow = emptyList()
        )
    }
}