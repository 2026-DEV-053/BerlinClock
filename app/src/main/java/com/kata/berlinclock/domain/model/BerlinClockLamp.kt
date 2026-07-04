package com.kata.berlinclock.domain.model

/**
 * Represents a single lamp in the Berlin Clock.
 *
 * A lamp has:
 * - a color (RED, YELLOW, or OFF)
 * - a state (on/off derived from color)
 */
data class BerlinClockLamp(
    val color: LampColor,
    val isOn: Boolean = color != LampColor.OFF
){
    companion object {
        /**
         * Creates a yellow lamp in ON or OFF state.
         */
        fun yellow(isOn: Boolean) = BerlinClockLamp(if (isOn) LampColor.YELLOW else LampColor.OFF, isOn)

        /**
         * Creates a red lamp in ON or OFF state.
         */
        fun red(isOn: Boolean) = BerlinClockLamp(if (isOn) LampColor.RED else LampColor.OFF, isOn)

        /**
         * Creates a lamp that is always OFF.
         */
        fun off() = BerlinClockLamp(LampColor.OFF, false)
    }
}