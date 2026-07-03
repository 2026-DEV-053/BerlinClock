package com.kata.berlinclock.domain.model

/**
 * Represents a snapshot of the current time.
 *
 * @property hours Hour of the day in 24-hour format (0–23).
 * @property minutes Minute of the hour (0–59).
 * @property seconds Second of the minute (0–59).
 */
data class TimeInput(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
)