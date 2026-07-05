package com.kata.berlinclock.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BerlinClockScreen(
    viewModel: BerlinClockViewModel = hiltViewModel()
){
    LaunchedEffect(Unit) {
        viewModel.startUpdatingClock()
    }
}