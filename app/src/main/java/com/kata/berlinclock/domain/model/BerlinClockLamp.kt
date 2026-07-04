package com.kata.berlinclock.domain.model

data class BerlinClockLamp(
    val color: LampColor,
    val isOn: Boolean = color != LampColor.OFF
){
    companion object {
        fun yellow(isOn: Boolean) = BerlinClockLamp(if (isOn) LampColor.YELLOW else LampColor.OFF, isOn)
        fun red(isOn: Boolean) = BerlinClockLamp(if (isOn) LampColor.RED else LampColor.OFF, isOn)
    }
}