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
)