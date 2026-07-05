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
){
    /**
     * Converts the time into a 24-hour digital clock format (HH:mm).
     *
     * The seconds component is intentionally omitted since it is not required
     * for display purposes.
     */
    fun toDisplayableDigitalTime(): String =
        "${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}"
}