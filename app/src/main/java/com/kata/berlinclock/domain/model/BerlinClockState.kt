package com.kata.berlinclock.domain.model

data class BerlinClockState(
    val secondsLamp: BerlinClockLamp,
    val fiveHourRow: List<BerlinClockLamp>,
    val oneHourRow: List<BerlinClockLamp>
)