package com.kata.berlinclock.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kata.berlinclock.domain.model.BerlinClockState
import com.kata.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for exposing the current Berlin Clock state to the UI.
 *
 * It observes the current system time through [ConvertTimeToBerlinClockUseCase]
 * and updates the UI state whenever the time changes.
 */
@HiltViewModel
class BerlinClockViewModel @Inject constructor(
    private val convertTimeToBerlinClockUseCase: ConvertTimeToBerlinClockUseCase
) : ViewModel() {

    /**
     * Internal mutable state holding the latest Berlin Clock representation.
     */
    private val _clockState = MutableStateFlow(BerlinClockState.init())

    /**
     * Read-only state exposed to the UI.
     *
     * The UI collects this [StateFlow] to receive updates whenever the
     * Berlin Clock state changes.
     */
    val clockState: StateFlow<BerlinClockState> = _clockState.asStateFlow()

    /**
     * Starts observing the current time and updates the clock state continuously.
     *
     * The observation runs within the ViewModel's coroutine scope and is
     * automatically canceled when the ViewModel is cleared.
     */
    fun startUpdatingClock(){
        viewModelScope.launch {
            convertTimeToBerlinClockUseCase.getCurrentClockState().collect { clockState ->
                _clockState.value = clockState
            }
        }
    }
}