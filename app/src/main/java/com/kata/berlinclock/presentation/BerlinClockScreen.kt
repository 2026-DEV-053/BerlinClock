package com.kata.berlinclock.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kata.berlinclock.domain.model.BerlinClockLamp
import com.kata.berlinclock.domain.model.BerlinClockState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BerlinClockScreen(
    viewModel: BerlinClockViewModel = hiltViewModel()
){
    val clockState = viewModel.clockState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startUpdatingClock()
    }
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        BerlinClockDisplay(
            clockState = clockState.value,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BerlinClockDisplay(
    clockState: BerlinClockState,
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))
        SecondLamp(clockState.secondsLamp)
    }
}

@Composable
private fun SecondLamp(
    lamp: BerlinClockLamp
) {
    val background = if(!lamp.isOn) Color.White else Color.Yellow

    Box(
        modifier = Modifier
            .testTag("SecondsLamp")
            .size(100.dp)
            .border(
                3.dp,
                Color.DarkGray,
                CircleShape
            )
            .background(background, CircleShape)
    )
}