package com.kata.berlinclock.presentation

import androidx.lifecycle.ViewModel
import com.kata.berlinclock.domain.model.BerlinClockState
import com.kata.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BerlinClockViewModel @Inject constructor(
    private val convertTimeToBerlinClockUseCase: ConvertTimeToBerlinClockUseCase
) : ViewModel() {

    private val _clockState = MutableStateFlow(BerlinClockState.init())
    val clockState: StateFlow<BerlinClockState> = _clockState.asStateFlow()
}